package idv.laborLab.exceptionHadler;

import idv.laborLab.exceptionHadler.globalException.LaborLabServiceException;
import idv.laborLab.exceptionHadler.util.ExceptionHandlerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ExceptionHandlerUtil exceptionHandlerUtil;

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointerException(NullPointerException nullPointerException) {

        ProblemDetail pd = exceptionHandlerUtil.buildProblemDetail(nullPointerException, 500);
        nullPointerException.printStackTrace();

        return ResponseEntity.of(pd).build();
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException runtimeException) {

        ProblemDetail pd = exceptionHandlerUtil.buildProblemDetail(runtimeException, 500);
        runtimeException.printStackTrace();

        return ResponseEntity.of(pd).build();
    }

    @ExceptionHandler(LaborLabServiceException.class)
    protected ResponseEntity<Object> handleLaborLabServiceException(LaborLabServiceException laborLabServiceException) {

        ProblemDetail pd = exceptionHandlerUtil.buildProblemDetail(laborLabServiceException, 500);
        laborLabServiceException.printStackTrace();

        return ResponseEntity.of(pd).build();
    }
}
