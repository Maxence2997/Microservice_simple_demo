package idv.laborLab.userService.business;

import idv.laborLab.userService.domain.UserDomainService;
import idv.laborLab.userService.dto.*;
import idv.laborLab.userService.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserBusinessServiceImpl implements UserBusinessService {

    private final UserDomainService userDomainService;

    @Override
    public long registerUser(UserRegistrationDTO user) {

        return userDomainService.registerUser(user);
    }

    @Override
    public UserDTO searchUser(UserGeneralRequestDTO userGeneralRequestDTO) {

        return userDomainService.searchUser(userGeneralRequestDTO.userIndex(), userGeneralRequestDTO.indexString());
    }

    @Override
    public UserDTO updateUser(UpdateUserInfoDTO updateUserInfoDTO) {
        //do some business check later

        User user = userDomainService.searchUserEntity(updateUserInfoDTO.userIndex(), updateUserInfoDTO.indexString());
        user.setEmail(updateUserInfoDTO.email());
        user.setPhoneNumber(updateUserInfoDTO.phoneNumber());
        return userDomainService.updateUser(user);
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

        User user = userDomainService.searchUserEntity(userGeneralRequestDTO.userIndex(), userGeneralRequestDTO.indexString());
        userDomainService.removeUser(user.getId());
    }

    @Override
    public void resetPassword(ResetUserPasswordDTO resetUserPasswordDTO) {

    }

    @Override
    public boolean checkUserExistence(UserGeneralRequestDTO userGeneralRequestDTO) {

        return userDomainService.checkUserExistence(userGeneralRequestDTO.userIndex(), userGeneralRequestDTO.indexString());
    }
}
