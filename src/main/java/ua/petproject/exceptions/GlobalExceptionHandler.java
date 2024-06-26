package ua.petproject.exceptions;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        String message = "Entity not found";
        log.error(message + ": {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(message);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        String message = "Illegal argument";
        log.error(message + ": {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(message);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        String message = "User already exists";
        log.error(message + ": {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(message);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        String message = "Access forbidden";
        log.error(message + ": {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(message);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}
