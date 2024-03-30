package org.mix.mixer.exception.userexception;

public class UserException extends RuntimeException {

    public UserException(String s) {
        super(s);
    }
    public UserException(){}

    public static UserException UserNotSavedPassword(){
        return new UserException("Не удалось сохранить пользователя");
    }
    public static UserException UserWrongPassword(){
        return new UserException("Неверный пароль");
    }
    public static UserException UserEqualsPassword(){
        return new UserException("Неправильный пароль");
    }
    public static UserException UserNotFound(){
        return new UserException("Пользователь не найден");
    }
}
