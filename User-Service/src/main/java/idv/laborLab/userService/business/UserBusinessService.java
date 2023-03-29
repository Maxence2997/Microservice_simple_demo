package idv.laborLab.userService.business;

import idv.laborLab.userService.dto.*;

public interface UserBusinessService {

    long registerUser(UserRegistrationDTO user);

    UserDTO searchUser(SearchUserRequestDTO searchUserRequestDTO);

    UserDTO updateUser(UpdateUserInfoDTO updateUserInfoDTO);

    boolean validateUser(SearchUserRequestDTO searchUserRequestDTO);

    void removeUser(SearchUserRequestDTO searchUserRequestDTO);

    // temporary
    void resetPassword(ResetUserPasswordDTO resetUserPasswordDTO);
}
