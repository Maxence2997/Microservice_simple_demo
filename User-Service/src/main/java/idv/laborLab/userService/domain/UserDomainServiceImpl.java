package idv.laborLab.userService.domain;

import idv.laborLab.userService.dto.*;
import idv.laborLab.userService.entity.User;
import idv.laborLab.userService.entity.UserSecurityInfo;
import idv.laborLab.userService.exception.UserNotFoundException;
import idv.laborLab.userService.repository.UserRepository;
import idv.laborLab.userService.repository.UserSecurityInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepository userRepository;
    private final UserSecurityInfoRepository userSecurityInfoRepository;
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

        long userId = userRepository.save(user).getId();
        //        String encryptedPassword = encryptionService.encrypt(userRegistrationDTO.password());

        UserSecurityInfo userSecurityInfo = UserSecurityInfo.builder()
                                                            .userId(userId)
                                                            //                                                            .password(encryptedPassword)
                                                            .passwordByte(encryptionService.encryptToByte(userRegistrationDTO.password()))
                                                            .build();

        userSecurityInfoRepository.save(userSecurityInfo);
        return userId;
    }

    @Override
    public UserDTO searchUser(UserIndex userIndex, String searchString) {

        return searchUserEntity(userIndex, searchString).convertToUserDTO();
    }

    @Override
    public User searchUserEntity(UserIndex userIndex, String searchString) {

        User user = new User();
        switch (userIndex) {
            case USER_NAME -> user = userRepository.findUserByUserName(searchString)
                                                   .orElseThrow(() -> new UserNotFoundException(searchString));
            case EMAIL -> user = userRepository.findUserByEmail(searchString)
                                               .orElseThrow(() -> new UserNotFoundException(searchString));
            case PHONE_NUMBER -> user = userRepository.findUserByPhoneNumber(searchString)
                                                      .orElseThrow(() -> new UserNotFoundException(searchString));
        }
        return user;
    }

    @Override
    public UserSecurityInfo searchUserSecurityInfo(long userId) {

        return userSecurityInfoRepository.findUserSecurityInfoByUserId(userId).orElseThrow();
    }

    @Override
    public boolean checkUserExistence(UserIndex userIndex, String searchString) {

        boolean result = false;

        switch (userIndex) {

            case USER_NAME -> result = userRepository.existsUserByUserName(searchString);
            case EMAIL -> result = userRepository.existsUserByEmail(searchString);
            case PHONE_NUMBER -> result = userRepository.existsUserByPhoneNumber(searchString);
        }

        return result;
    }

    @Override
    public boolean matchPassword(long userId, String password) {

        byte[] passwordByte = this.searchUserSecurityInfo(userId).getPasswordByte();
        return encryptionService.decrypt(passwordByte).equals(password);
    }

    @Override
    public UserDTO updateUser(User user) {

        return userRepository.save(user).convertToUserDTO();
    }

    @Override
    public boolean validateUser(UserGeneralRequestDTO userGeneralRequestDTO) {

        return false;
    }

    @Override
    public void removeUser(long userId) {

        userRepository.deleteById(userId);
    }

    @Override
    public void resetPassword(ResetUserPasswordDTO resetUserPasswordDTO) {

    }
}
