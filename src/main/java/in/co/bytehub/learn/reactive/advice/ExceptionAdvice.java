package in.co.bytehub.learn.reactive.advice;

import in.co.bytehub.learn.reactive.exception.BaseException;
import in.co.bytehub.learn.reactive.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleException(BaseException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        ResponseEntity<ErrorResponse> errorResponseResponseEntity = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        if (ex instanceof RecordNotFoundException) {
            errorResponseResponseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        return errorResponseResponseEntity;
    }
}
