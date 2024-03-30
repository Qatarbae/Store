package org.mix.mixer.controller.user;

import lombok.RequiredArgsConstructor;
import org.mix.mixer.exception.userexception.UserException;
import org.mix.mixer.model.usermodel.ChangePasswordRequest;
import org.mix.mixer.model.usermodel.UserViewModel;
import org.mix.mixer.service.userservice.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(
            @RequestParam ChangePasswordRequest request,
            Principal connectedUser
    ) {
        try {
            service.changePassword(request, connectedUser);
            return ResponseEntity.ok().body("Вы сменили пароль");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("произошла ошибка");
        }

    }

    @GetMapping("/")
    public ResponseEntity<Object> getUsers(
            @RequestParam(name = "username") String username
    ){
        try {
             return ResponseEntity.ok().body(service.getUser(username));
        } catch (UserException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{count}/before")
    @ResponseBody
    public ResponseEntity<Object> getUsers(
            @PathVariable("count") int count
    ){
        try {
            List<UserViewModel> users = service.getUsersBefore(count);
            return ResponseEntity.ok().body(users);
        } catch (UserException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/")
    @Transactional
    public ResponseEntity<Object> deleteUser(
            @RequestParam(name = "password") String password,
            Principal connectedUser
    ){
        try {
            service.deleteUser(password, connectedUser);
            return ResponseEntity.ok().build();
        } catch (UserException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}