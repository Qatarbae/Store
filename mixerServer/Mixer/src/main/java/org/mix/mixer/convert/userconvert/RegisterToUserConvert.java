package org.mix.mixer.convert.userconvert;

import lombok.RequiredArgsConstructor;
import org.mix.mixer.model.auth.RegisterViewModel;
import org.mix.mixer.entity.appuser.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class RegisterToUserConvert implements UserConvert<User, RegisterViewModel>{
    private final PasswordEncoder passwordEncoder;
    @Override
    public User toConvert(RegisterViewModel model) {
        return User.builder()
                .firstname(model.getFirstname())
                .lastname(model.getLastname())
                .email(model.getEmail())
                .password(passwordEncoder.encode(model.getPassword()))
                .role(model.getRole())
                .build();
    }

}
