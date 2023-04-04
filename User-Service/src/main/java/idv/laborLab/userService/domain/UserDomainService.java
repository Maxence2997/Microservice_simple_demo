package idv.laborLab.userService.domain;

import idv.laborLab.sharedLibrary.objects.UserRegistrationSO;
import idv.laborLab.userService.dto.*;
import idv.laborLab.userService.entity.User;
import idv.laborLab.userService.entity.UserSecurityInfo;

public interface UserDomainService {

    long registerUser(UserRegistrationDTO user);

    UserDTO searchUser(UserIndex userIndex, String searchString);

    User searchUserEntity(UserIndex userIndex, String searchString);

    UserSecurityInfo searchUserSecurityInfo(long userId);

    boolean checkUserExistence(UserIndex userIndex, String searchString);

    boolean matchPassword(long userId, String password);

    UserDTO updateUser(User user);

    boolean validateUser(UserGeneralRequestDTO userGeneralRequestDTO);

    void removeUser(long userId);

    // temporary
    void resetPassword(ResetUserPasswordDTO resetUserPasswordDTO);

    void saveUserInfo(UserRegistrationSO userRegistrationSO);
}
