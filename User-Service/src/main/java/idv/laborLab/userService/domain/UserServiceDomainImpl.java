package idv.laborLab.userService.domain;

import idv.laborLab.userService.dao.UserDaoService;
import idv.laborLab.userService.dto.*;
import idv.laborLab.userService.entity.User;
import idv.laborLab.userService.entity.UserSecurityInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceDomainImpl implements UserDomainService {

    private final UserDaoService userDaoService;
    private final EncryptionService encryptionService;

    @Override
    public long registerUser(UserRegistrationDTO userRegistrationDTO) {

        User user = User.builder()
                        .userName(userRegistrationDTO.userName())
                        .firstName(userRegistrationDTO.firstName())
                        .lastName(userRegistrationDTO.lastName())
                        .email(userRegistrationDTO.email())
                        .phoneNumber(userRegistrationDTO.phoneNumber())
                        .dateOfBirth(userRegistrationDTO.dateOfBirth())
                        .addressId(0)//temp
                        .build();

        long userId = userDaoService.saveUserIntoDatabase(user);
        String encryptedPassword = encryptionService.encrypt(userRegistrationDTO.password());
        UserSecurityInfo userSecurityInfo = UserSecurityInfo.builder()
                                                            .userId(userId)
                                                            .password(encryptedPassword)
                                                            .build();

        userDaoService.saveUserSecurityInfoIntoDatabase(userSecurityInfo);
        return userId;
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
