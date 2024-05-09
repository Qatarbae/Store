package org.mix.mixer.controller.user;

import lombok.RequiredArgsConstructor;
import org.mix.mixer.model.usermodel.ChangePasswordRequest;
import org.mix.mixer.model.usermodel.UserViewModel;
import org.mix.mixer.service.userservice.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().body("Вы сменили пароль");
    }

    @GetMapping("/")
    public ResponseEntity<?> getUser(
            @RequestParam(name = "username") String username
    ) {
        return ResponseEntity.ok().body(service.getUser(username));
    }

    @GetMapping("/{count}/before")
    public ResponseEntity<?> getUsersBefore(
            @PathVariable("count") int count
    ) {
        List<UserViewModel> users = service.getUsersBefore(count);
        return ResponseEntity.ok().body(users);
    }

    @DeleteMapping("/")
    @Transactional
    public ResponseEntity<?> deleteUser(
            @RequestParam(name = "password") String password,
            Principal connectedUser
    ) {
        service.deleteUser(password, connectedUser);
        return ResponseEntity.ok().build();
    }
}