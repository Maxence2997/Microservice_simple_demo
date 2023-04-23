package idv.laborLab.userService.business;

import idv.laborLab.userService.domain.UserDomainService;
import idv.laborLab.userService.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserBusinessServiceImpl implements UserBusinessService {

    private final UserDomainService userDomainService;

    @Override
    public FindUsersResponseDTO findUsers(int page, int size) {

        return userDomainService.findUsers(page, size);
    }

    @Override
    public long registerUser(UserRegistrationDTO user) {

        return userDomainService.registerUser(user);
    }

    @Override
    public UserDTO searchUser(UserGeneralRequestDTO userGeneralRequestDTO) {

        return userDomainService.searchUser(userGeneralRequestDTO.userIndex(), userGeneralRequestDTO.indexString());
    }

    @Override
    public UserDTO searchUserByUserName(String userName) {

        return userDomainService.searchUserByUserName(userName);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        //do some business check later

        return userDomainService.updateUser(userDTO);
    }

    @Override
    public boolean validateUser(UserGeneralRequestDTO userGeneralRequestDTO) {

        return false;
    }

    @Override
    public boolean logInUser(UserLogInDTO userLogInDTO) {

        UserDTO user = userDomainService.searchUser(userLogInDTO.userIndex(), userLogInDTO.IndexString());
        return userDomainService.matchPassword(user.getUserId(), userLogInDTO.password());
    }

    @Override
    public void removeUser(UserGeneralRequestDTO userGeneralRequestDTO) {

        UserDTO userDTO = userDomainService.searchUser(userGeneralRequestDTO.userIndex(), userGeneralRequestDTO.indexString());
        userDomainService.removeUser(userDTO);
    }

    @Override
    public void resetPassword(ResetUserPasswordDTO resetUserPasswordDTO) {

    }

    @Override
    public boolean checkUserExistence(UserGeneralRequestDTO userGeneralRequestDTO) {

        return userDomainService.checkUserExistence(userGeneralRequestDTO.userIndex(), userGeneralRequestDTO.indexString());
    }
}
