package idv.laborLab.exceptionHadler.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExceptionHandlerUtil {

    public ProblemDetail buildProblemDetail(Exception exception, int responseCode) {

        String details = exception.getClass().getName() + ": " + exception.getLocalizedMessage();
        log.error(details);
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(responseCode), details);
    }
}
