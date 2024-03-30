package com.jwt.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.security.Entity.token.Token;
import com.jwt.security.Entity.token.TokenRepository;
import com.jwt.security.Entity.token.TokenType;
import com.jwt.security.Entity.user.Role;
import com.jwt.security.Entity.user.User;
import com.jwt.security.repository.UserRepository;
import com.jwt.security.config.RateLimited;
import com.jwt.security.exception.CustomException;
import com.jwt.security.requestResponse.AuthenticationRequest;
import com.jwt.security.requestResponse.AuthenticationResponse;
import com.jwt.security.requestResponse.RegisterRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public AuthenticationResponse register(RegisterRequest request) {
        try {
            Role role = null;
            if(request.getRole() != null  && (request.getRole().equals("ADMIN") || request.getRole().equals("MANAGER"))){
                role = Role.valueOf(request.getRole());
            }
            else {
                role = Role.USER;
            }
            var user = User.builder()
                    .firstname(request.getFirstName())
                    .lastname(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            repository.save(user);
            System.out.println(user.getRole());
            var savedUser = repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(savedUser, jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (DataIntegrityViolationException e) {
            // Обработка ошибки, когда пользователь с таким же именем уже существует
            throw new CustomException("Пользователь с таким именем уже существует.");
        } catch (Exception e) {
            // Обработка других ошибок при сохранении пользователя
            System.out.println(e.getMessage());
            throw new CustomException("Не удалось сохранить пользователя.");
        }

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();

    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @RateLimited(value = "myMethod", limit = 1, duration = 1)
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                var _refreshToken = jwtService.generateRefreshToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(_refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public String updatePasswordToken(String login, String updatePasswordRequestId){
        return jwtService.updatePasswordToken(login ,updatePasswordRequestId);
    }
    public String updatePassword(String token){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            String login = claims.getBody().get("login", String.class);
            String updatePasswordRequestId = claims.getBody().get("updatePasswordRequestId", String.class);
            Optional<User> optionalUserser = repository.findByEmail(login);
            if(optionalUserser.isEmpty()){
                return "пользователя нет";
            }
            else{
                User user = optionalUserser.get();
                user.setPassword(passwordEncoder.encode("1111"));
                repository.save(user);
                return "Пароль успешно обновлен для пользователя с ID: " + login;
            }
        } catch (Exception ex) {
            return "Неверный токен или истек срок его действия.";
        }
    }

    // Вместо ConcurrentMap здесь вы можете использовать базу данных для хранения связи между updatePasswordRequestId и пользователем
    private final ConcurrentMap<String, String> updatePasswordRequests = new ConcurrentHashMap<>();

    // Метод для инициирования запроса на обновление пароля
    public String initPasswordResetRequest(String login) {
        // Генерация уникального идентификатора для запроса на обновление пароля
        String updatePasswordRequestId = UUID.randomUUID().toString();

        // Сохранение связи между updatePasswordRequestId и userId (или другой информацией, которая может быть полезной при обработке запроса)
        updatePasswordRequests.put(updatePasswordRequestId, login);

        return updatePasswordRequestId;
    }

    // Метод для получения информации о пользователе по updatePasswordRequestId
    public String getUserIdByUpdatePasswordRequestId(String updatePasswordRequestId) {
        return updatePasswordRequests.get(updatePasswordRequestId);
    }
}
