package edu.pet.tasktrackerapi.dao.exception_handler;

import edu.pet.tasktrackerapi.dao.exception.BadCredentialsException;
import edu.pet.tasktrackerapi.dao.exception.BadRequestException;
import edu.pet.tasktrackerapi.dao.exception.NotFoundException;
import edu.pet.tasktrackerapi.dao.exception.UserExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
        ErrorMessage errorBody = new ErrorMessage("Bad request!");

        return handleExceptionInternal(ex, errorBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        ErrorMessage errorBody = new ErrorMessage("Bad credentials!");

        return handleExceptionInternal(ex, errorBody, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);

    }

    @ExceptionHandler(value = UserExistsException.class)
    public ResponseEntity<Object> handleUserExistsException(UserExistsException ex, WebRequest request) {
        ErrorMessage errorBody = new ErrorMessage("This username is already taken!");

        return handleExceptionInternal(ex, errorBody, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e, WebRequest request) {
        log.error(e.getMessage());
        ErrorMessage errorBody = new ErrorMessage("Not found!");

        return handleExceptionInternal(e, errorBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }


}
