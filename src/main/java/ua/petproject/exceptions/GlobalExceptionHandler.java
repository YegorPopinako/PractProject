package ua.petproject.exceptions;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class,
            IllegalArgumentException.class,
            MethodArgumentNotValidException.class})
    @ResponseStatus
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(Exception ex) {
        String message;
        HttpStatus status = switch (ex.getClass().getSimpleName()) {
            case "EntityNotFoundException" -> {
                message = "Entity not found";
                yield HttpStatus.NOT_FOUND;
            }
            case "IllegalArgumentException" -> {
                message = "Illegal argument";
                yield HttpStatus.BAD_REQUEST;
            }
            case "MethodArgumentNotValidException" -> {
                message = "Method argument not valid";
                yield HttpStatus.BAD_REQUEST;
            }
            default -> {
                message = "Internal server error";
                yield HttpStatus.INTERNAL_SERVER_ERROR;
            }
        };
        log.error("Entity not found: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(message);
        return new ResponseEntity<>(errorResponse, status);
    }

/*    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Illegal argument: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse("Illegal argument");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Method argument not valid: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse("Method argument not valid");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }*/
}
