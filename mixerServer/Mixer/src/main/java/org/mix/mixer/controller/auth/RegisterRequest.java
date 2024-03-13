package org.mix.mixer.controller.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mix.mixer.model.appuser.UserRole;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private UserRole role;
}
