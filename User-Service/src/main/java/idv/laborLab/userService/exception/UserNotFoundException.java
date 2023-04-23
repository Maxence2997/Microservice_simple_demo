package idv.laborLab.userService.exception;

import idv.laborLab.exceptionHadler.globalException.LaborLabServiceException;
import idv.laborLab.userService.dto.UserIndex;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotFoundException extends LaborLabServiceException {

    public static final String USER_NOT_FOUND_ERROR_MSG = "Can't find an user with ";


    public UserNotFoundException(UserIndex userIndex, String value, Throwable err) {
        super(USER_NOT_FOUND_ERROR_MSG + "index: " + userIndex.name() + ", value: " + value, err);
        log.error(USER_NOT_FOUND_ERROR_MSG + "index: " + userIndex.name() + ", value: " + value);
    }
    public UserNotFoundException(UserIndex userIndex, String value) {

        super(USER_NOT_FOUND_ERROR_MSG + "index: " + userIndex.name() + ", value: " + value);
        log.error(USER_NOT_FOUND_ERROR_MSG + "index: " + userIndex.name() + ", value: " + value);
    }

    public UserNotFoundException(String value) {

        super(USER_NOT_FOUND_ERROR_MSG + value);
        log.error(USER_NOT_FOUND_ERROR_MSG + value);
    }
}
