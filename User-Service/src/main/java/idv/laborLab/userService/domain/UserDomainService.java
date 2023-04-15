package idv.laborLab.userService.domain;

import idv.laborLab.sharedLibrary.objects.UserRegistrationSO;
import idv.laborLab.userService.dto.*;
import idv.laborLab.userService.entity.User;
import idv.laborLab.userService.entity.UserSecurityInfo;

public interface UserDomainService {

    long registerUser(UserRegistrationDTO user);

    void registerUserPostProcess(UserRegistrationSO userRegistrationSO);

    UserDTO searchUser(UserIndex userIndex, String searchString);

    UserDTO searchUserByUserName(String userName);

    User searchUserEntity(UserIndex userIndex, String searchString);

    UserSecurityInfo searchUserSecurityInfo(long userId);

    boolean checkUserExistence(UserIndex userIndex, String searchString);

    boolean matchPassword(long userId, String password);

    UserDTO updateUser(UserDTO userDTO);

    boolean validateUser(UserGeneralRequestDTO userGeneralRequestDTO);

    void removeUser(UserDTO userDTO);

    // temporary
    void resetPassword(ResetUserPasswordDTO resetUserPasswordDTO);
}
