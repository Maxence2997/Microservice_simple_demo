package idv.laborLab.userservice.business;

import idv.laborLab.userservice.dto.SearchUserRequestDTO;
import idv.laborLab.userservice.dto.UserDTO;
import idv.laborLab.userservice.dto.UserRegistrationDTO;

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
