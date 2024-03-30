package org.mix.mixer.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mix.mixer.entity.appuser.UserRole;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterViewModel {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private UserRole role;
}
