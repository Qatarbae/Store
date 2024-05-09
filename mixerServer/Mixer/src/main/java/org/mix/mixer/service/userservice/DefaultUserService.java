package org.mix.mixer.service.userservice;

import lombok.RequiredArgsConstructor;
import org.mix.mixer.convert.userconvert.UserConvert;
import org.mix.mixer.entity.appuser.User;
import org.mix.mixer.exception.userexception.UserException;
import org.mix.mixer.model.usermodel.ChangePasswordRequest;
import org.mix.mixer.model.usermodel.UserViewModel;
import org.mix.mixer.repository.userrepository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserConvert<UserViewModel, User> userToUserViewModel;

    @Override
    public void changePassword(ChangePasswordRequest request,
                               Principal connectedUser) throws UserException {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw UserException.UserWrongPassword();
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw UserException.UserEqualsPassword();
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }

    @Override
    public UserViewModel getUser(String username) throws UserException {
        User user = userRepository.findByFirstname(username).orElseThrow(UserException::UserNotFound);
        return userToUserViewModel.toConvert(user);
    }

    @Override
    public void deleteUser(String password, Principal connectedUser) throws UserException {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        User deleteUser = userRepository.findByEmail(user.getEmail()).orElseThrow(UserException::UserNotFound);

        if (!passwordEncoder.matches(password, deleteUser.getPassword())) {
            throw new UserException("Неверный пароль");
        }
        userRepository.delete(deleteUser);
    }

    @Override
    public List<UserViewModel> getUsersBefore(Integer count) {
        List<UserViewModel> userViewModels = userRepository
                .findFirstNOrderByUsernameAsc(count)
                .stream()
                .map(userToUserViewModel::toConvert)
                .toList();
        return userViewModels;
    }
}
