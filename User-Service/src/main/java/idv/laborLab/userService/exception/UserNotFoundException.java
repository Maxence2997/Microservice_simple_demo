package idv.laborLab.userService.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotFoundException extends RuntimeException {

    public static final String USER_NOT_FOUND_ERROR_MSG = "Can't find an user with verbiage: ";

    public UserNotFoundException(String userId, Throwable err) {

        super(userId, err);
        log.error(USER_NOT_FOUND_ERROR_MSG + userId);
    }

    public UserNotFoundException(String userId) {

        super(USER_NOT_FOUND_ERROR_MSG + userId);
        log.error(USER_NOT_FOUND_ERROR_MSG + userId);
    }
}
