package idv.laborLab.userService.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class UserServiceExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleRuntimeException(UserNotFoundException userNotFoundException) {
        String details = userNotFoundException.getLocalizedMessage();
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), details);
        log.error(details);
//        userNotFoundException.printStackTrace();
        return ResponseEntity.of(pd).build();
    }
}
