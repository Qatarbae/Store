package org.mix.mixer.model.usermodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mix.mixer.entity.appuser.UserRole;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserViewModel {

    private String username;
    private String firstname;
    private String lastname;
    private UserRole role;
}
