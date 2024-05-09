package org.mix.mixer.controller.exception;

import lombok.RequiredArgsConstructor;
import org.mix.mixer.exception.userexception.UserAuthException;
import org.mix.mixer.exception.userexception.UserException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;
import java.util.Objects;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler({UserException.class, RuntimeException.class})
    public ResponseEntity<ProblemDetail> handlerUserException(
            UserException exception,
            Locale locale
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        Objects.requireNonNull(this.messageSource.getMessage(
                                exception.getMessage(),
                                new Object[0],
                                exception.getMessage(), locale))));
    }

    @ExceptionHandler(UserAuthException.class)
    public ResponseEntity<Object> handleUserAuthException(
            UserAuthException exception,
            Locale locale
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                        Objects.requireNonNull(this.messageSource.getMessage(
                                exception.getMessage(),
                                new Object[0],
                                exception.getMessage(), locale))));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(
            Exception ex,
            Locale locale
    ) {
        return ResponseEntity.badRequest().body("Произошла ошибка");
    }
}
