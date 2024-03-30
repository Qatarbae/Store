package org.mix.mixer.controller.auth;


import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mix.mixer.exception.userexception.UserAuthException;
import org.mix.mixer.model.auth.AuthViewModel;
import org.mix.mixer.model.auth.RegisterViewModel;
import org.mix.mixer.service.auth.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<Object> register(@RequestBody RegisterViewModel request) {
        try {

            return ResponseEntity.ok(service.register(request));

        } catch (UserAuthException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password
    ) {
        try {

            return ResponseEntity.ok(service.authenticate(email, password));

        } catch (UserAuthException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }

    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, java.io.IOException {
        service.refreshToken(request, response);
    }


}