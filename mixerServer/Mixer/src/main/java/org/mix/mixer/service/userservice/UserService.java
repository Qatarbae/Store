package org.mix.mixer.service.userservice;

import lombok.RequiredArgsConstructor;
import org.mix.mixer.convert.userconvert.UserConvert;
import org.mix.mixer.exception.userexception.UserException;
import org.mix.mixer.model.usermodel.ChangePasswordRequest;
import org.mix.mixer.entity.appuser.User;
import org.mix.mixer.model.usermodel.UserViewModel;
import org.mix.mixer.repository.userrepository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserConvert<UserViewModel, User> userToUserViewModel;
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

    public UserViewModel getUser(String username) throws UserException {
        User user = userRepository.findByFirstname(username).orElseThrow();
        return userToUserViewModel.toConvert(user);
    }

    public void deleteUser(String password, Principal connectedUser) throws UserException {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            if (passwordEncoder.matches(password, existingUser.getPassword())) {
                userRepository.delete(existingUser);
            } else {
                throw new UserException("Неверный пароль");
            }
        } else {
            throw new UserException("Пользователь не найден");
        }
    }

    public List<UserViewModel> getUsersBefore(Integer count){
        List<UserViewModel> userViewModels = userRepository
                .findFirstNOrderByUsernameAsc(count)
                .stream()
                .map(userToUserViewModel::toConvert)
                .toList();
        return userViewModels;
    }
}
