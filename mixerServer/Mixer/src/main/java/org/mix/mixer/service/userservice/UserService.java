package org.mix.mixer.service.userservice;

import org.mix.mixer.exception.userexception.UserException;
import org.mix.mixer.model.usermodel.ChangePasswordRequest;
import org.mix.mixer.model.usermodel.UserViewModel;

import java.security.Principal;
import java.util.List;

public interface UserService {
    void changePassword(ChangePasswordRequest request, Principal connectedUser) throws UserException;

    UserViewModel getUser(String username) throws UserException;

    void deleteUser(String password, Principal connectedUser) throws UserException;

    List<UserViewModel> getUsersBefore(Integer count);
}
