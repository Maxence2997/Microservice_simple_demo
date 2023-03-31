package idv.laborLab.userService.business;

import idv.laborLab.userService.domain.UserDomainService;
import idv.laborLab.userService.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserBusinessImpl implements UserBusinessService {

    private final UserDomainService userDomainService;

    @Override
    public long registerUser(UserRegistrationDTO user) {

        return userDomainService.registerUser(user);
    }

    @Override
    public UserDTO searchUser(SearchUserRequestDTO searchUserRequestDTO) {

        return null;
    }

    @Override
    public UserDTO updateUser(UpdateUserInfoDTO updateUserInfoDTO) {

        return null;
    }

    @Override
    public boolean validateUser(SearchUserRequestDTO searchUserRequestDTO) {

        return false;
    }

    @Override
    public void removeUser(SearchUserRequestDTO searchUserRequestDTO) {

    }

    @Override
    public void resetPassword(ResetUserPasswordDTO resetUserPasswordDTO) {

    }
}
