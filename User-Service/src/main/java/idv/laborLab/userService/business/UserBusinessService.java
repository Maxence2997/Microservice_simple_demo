package idv.laborLab.userService.business;

import idv.laborLab.userService.dto.SearchUserRequestDTO;
import idv.laborLab.userService.dto.UserDTO;
import idv.laborLab.userService.dto.UserRegistrationDTO;

public interface UserBusinessService {

    // register user
    UserDTO registerUser(UserRegistrationDTO user);

    // get specific user
    UserDTO searchUser(SearchUserRequestDTO searchUserRequestDTO);

    // update user info

    // remove user

    // validate user

    // reset password
}
