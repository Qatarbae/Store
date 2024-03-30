package org.mix.mixer.convert.userconvert;

import lombok.RequiredArgsConstructor;
import org.mix.mixer.entity.appuser.User;
import org.mix.mixer.model.usermodel.UserViewModel;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserToUserViewModelConvert implements UserConvert<UserViewModel, User>{
    @Override
    public UserViewModel toConvert(User model) {
        return UserViewModel.builder()
                .firstname(model.getFirstname())
                .lastname(model.getLastname())
                .role(model.getRole())
                .build();
    }
}
