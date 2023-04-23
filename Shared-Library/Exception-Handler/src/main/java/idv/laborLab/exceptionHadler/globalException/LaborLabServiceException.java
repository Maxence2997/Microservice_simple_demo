package idv.laborLab.exceptionHadler.globalException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LaborLabServiceException extends RuntimeException {

    public LaborLabServiceException(String msg, Throwable err) {

        super(msg, err);
        log.error(msg);
    }

    public LaborLabServiceException(String msg) {

        super(msg);
        log.error(msg);
    }
}
