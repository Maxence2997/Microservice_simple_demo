package idv.laborLab.userService.exception;

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
public class UserServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private final ExceptionHandlerUtil exceptionHandlerUtil;

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException) {

        ProblemDetail pd = exceptionHandlerUtil.buildProblemDetail(userNotFoundException, 400);
        userNotFoundException.printStackTrace();

        return ResponseEntity.of(pd).build();
    }

    @ExceptionHandler(UserInformationConflictException.class)
    protected ResponseEntity<Object> handleUserInformationConflictException(UserInformationConflictException userInformationConflictException) {

        ProblemDetail pd = exceptionHandlerUtil.buildProblemDetail(userInformationConflictException, 400);
        userInformationConflictException.printStackTrace();

        return ResponseEntity.of(pd).build();
    }
}
