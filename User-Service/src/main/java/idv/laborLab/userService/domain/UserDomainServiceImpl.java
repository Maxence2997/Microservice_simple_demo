package idv.laborLab.userService.domain;

import idv.laborLab.queueGateway.queueService.UserRegistrationQueueService;
import idv.laborLab.sharedLibrary.objects.UserRegistrationSO;
import idv.laborLab.userService.dto.*;
import idv.laborLab.userService.entity.User;
import idv.laborLab.userService.entity.UserSecurityInfo;
import idv.laborLab.userService.exception.UserNotFoundException;
import idv.laborLab.userService.repository.UserRepository;
import idv.laborLab.userService.repository.UserSecurityInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@CacheConfig(cacheNames = "userService")
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepository userRepository;
    private final UserSecurityInfoRepository userSecurityInfoRepository;
    private final EncryptionService encryptionService;
    private final UserRegistrationQueueService userRegistrationQueueService;

    @Override
    public long registerUser(UserRegistrationDTO userRegistrationDTO) {

        // write into Redis first and then convert to queue for mysql
        UserRegistrationSO userRegistrationSO = userRegistrationDTO.convertToUserRegistrationSO();

        userRegistrationQueueService.convertAndSend(userRegistrationSO);

        return 0;
    }

    @Cacheable(key = "#searchString") // testing
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

    @Override
    public void saveUserInfo(UserRegistrationSO userRegistrationSO) {

        User user = User.builder()
                        .userName(userRegistrationSO.getUserName())
                        .firstName(userRegistrationSO.getFirstName())
                        .lastName(userRegistrationSO.getLastName())
                        .email(userRegistrationSO.getEmail())
                        .phoneNumber(userRegistrationSO.getPhoneNumber())
                        .dateOfBirth(userRegistrationSO.getDateOfBirth())
                        .addressId(0)                           //temp
                        .build();

        long userId = userRepository.save(user).getId();

        UserSecurityInfo userSecurityInfo = UserSecurityInfo.builder()
                                                            .userId(userId)
                                                            .passwordByte(encryptionService.encryptToByte(userRegistrationSO.getPassword()))
                                                            .build();

        userSecurityInfoRepository.save(userSecurityInfo);
    }
}
