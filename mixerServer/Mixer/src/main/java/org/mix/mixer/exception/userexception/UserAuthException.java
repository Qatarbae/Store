package org.mix.mixer.exception.userexception;

public class UserAuthException extends RuntimeException{
    public UserAuthException() {
    }

    public UserAuthException(String message) {
        super(message);
    }

    public static UserAuthException UserNotRegister(String message){
        return new UserAuthException(message);
    }

    public static UserAuthException UserNotAuthentication(String message){
        return new UserAuthException();
    }
}
