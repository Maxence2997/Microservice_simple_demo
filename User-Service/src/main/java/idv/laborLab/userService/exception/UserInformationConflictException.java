package idv.laborLab.userService.exception;

import idv.laborLab.exceptionHadler.globalException.LaborLabServiceException;
import idv.laborLab.userService.dto.UserIndex;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserInformationConflictException extends LaborLabServiceException {

    public static final String USER_INFORMATION_CONFLICT_ERROR_MSG = "User key information conflict in database, ";

    public UserInformationConflictException(UserIndex userIndex, String conflictContent, Throwable err) {

        super(USER_INFORMATION_CONFLICT_ERROR_MSG + "field: " + userIndex.name() + ", conflict content: " + conflictContent, err);
        log.error(USER_INFORMATION_CONFLICT_ERROR_MSG + "field: " + userIndex + ", conflict content: " + conflictContent);
    }

    public UserInformationConflictException(UserIndex userIndex, String conflictContent) {

        super(USER_INFORMATION_CONFLICT_ERROR_MSG + "field: " + userIndex.name()  + ", conflict content: " + conflictContent);
        log.error(USER_INFORMATION_CONFLICT_ERROR_MSG + "field: " + userIndex + ", conflict content: " + conflictContent);
    }

    public UserInformationConflictException(String conflictContent) {

        super(USER_INFORMATION_CONFLICT_ERROR_MSG + conflictContent);
        log.error(USER_INFORMATION_CONFLICT_ERROR_MSG + conflictContent);
    }
}
