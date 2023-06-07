package com.jwt.security.auth;

import com.jwt.security.Entity.user.*;
import com.jwt.security.Entity.user.repository.RoleRepository;
import com.jwt.security.Entity.user.repository.UserRepository;
import com.jwt.security.config.JwtService;
import com.jwt.security.exception.YourCustomException;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    public AuthenticationResponse register(RegisterRequest request) {
        try {

        Optional<Roles> rolesOptional = roleRepository.findByRole("ADMIN");
        Roles roles = rolesOptional.orElse(null);
        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roles)
                .build();
        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
        } catch (DataIntegrityViolationException e) {
            // Обработка ошибки, когда пользователь с таким же именем уже существует
            throw new YourCustomException("Пользователь с таким именем уже существует.");
        } catch (Exception e) {
            // Обработка других ошибок при сохранении пользователя
            throw new YourCustomException("Не удалось сохранить пользователя.");
        }

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }
}
