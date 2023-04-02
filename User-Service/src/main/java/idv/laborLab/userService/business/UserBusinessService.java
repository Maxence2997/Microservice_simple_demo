package idv.laborLab.userService.business;

import idv.laborLab.userService.dto.*;

public interface UserBusinessService {

    long registerUser(UserRegistrationDTO user);

    UserDTO searchUser(UserGeneralRequestDTO userGeneralRequestDTO);

    UserDTO updateUser(UpdateUserInfoDTO updateUserInfoDTO);

    boolean validateUser(UserGeneralRequestDTO userGeneralRequestDTO);

    boolean logInUser(UserLogInDTO userLogInDTO);

    void removeUser(UserGeneralRequestDTO userGeneralRequestDTO);

    // temporary
    void resetPassword(ResetUserPasswordDTO resetUserPasswordDTO);

    boolean checkUserExistence(UserGeneralRequestDTO userGeneralRequestDTO);
}
