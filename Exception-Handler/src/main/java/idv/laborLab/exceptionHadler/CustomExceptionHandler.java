package idv.laborLab.exceptionHadler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointerException(NullPointerException nullPointerException) {

        String details = "NullPointerException" + ": " + nullPointerException.getLocalizedMessage();
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), details);
        log.error(details);
        nullPointerException.printStackTrace();
        return ResponseEntity.of(pd).build();
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException runtimeException) {
        String details = "RuntimeException" + ": " + runtimeException.getLocalizedMessage();
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), details);
        log.error(details);
        runtimeException.printStackTrace();;
        return ResponseEntity.of(pd).build();
    }
}
