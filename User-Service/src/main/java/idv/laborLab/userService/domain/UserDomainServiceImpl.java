package idv.laborLab.userService.domain;

import idv.laborLab.queueGateway.queueService.UserRegistrationQueueService;
import idv.laborLab.redisClient.annotation.LaborLabCacheEvict;
import idv.laborLab.redisClient.annotation.LaborLabCachePut;
import idv.laborLab.redisClient.annotation.LaborLabCacheable;
import idv.laborLab.redisClient.repo.RedisGeneralValueRepository;
import idv.laborLab.sharedLibrary.miscellaneous.objects.UserRegistrationSO;
import idv.laborLab.sharedLibrary.miscellaneous.services.IDService;
import idv.laborLab.userService.dto.*;
import idv.laborLab.userService.entity.User;
import idv.laborLab.userService.entity.UserSecurityInfo;
import idv.laborLab.userService.exception.UserNotFoundException;
import idv.laborLab.userService.repo.UserRedisRepository;
import idv.laborLab.userService.repo.UserRepository;
import idv.laborLab.userService.repo.UserSecurityInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepository userRepository;
    private final UserSecurityInfoRepository userSecurityInfoRepository;
    private final EncryptionService encryptionService;
    private final UserRegistrationQueueService userRegistrationQueueService;
    private final IDService redisIDService;
    private final UserRedisRepository userRedisRepository;
    private final RedisGeneralValueRepository redisGeneralValueRepository;

    @Override
    public long registerUser(UserRegistrationDTO userRegistrationDTO) {

        // check redis cache is there any conflict

        // write into Redis first

        User user = userRegistrationDTO.convertToUserEntity();
        long newId = redisIDService.getNextID();
        user.setId(newId);
        userRedisRepository.save(user);
        CompletableFuture.runAsync(() -> {

            byte[] encryptedPassword = encryptionService.encryptToByte(userRegistrationDTO.getPassword());

            // convert to shared object and send it to queue for mysql
            userRegistrationQueueService.convertAndSend(userRegistrationDTO.buildUserRegistrationSO(newId, encryptedPassword));
        });

        return newId;
    }

    @Override
    public void registerUserPostProcess(UserRegistrationSO userRegistrationSO) {

        log.info("register user post process start");

        User user = User.buildFromUserRegistrationSO(userRegistrationSO);
        log.info("save data into database: {}", user);
        userRepository.save(user);

        UserSecurityInfo userSecurityInfo = UserSecurityInfo.buildFromUserRegistrationSO(userRegistrationSO);
        log.info("save data into database: {}", userSecurityInfo);
        userSecurityInfoRepository.save(userSecurityInfo);

        log.info("register user process terminate");
    }

    @Override
    public UserDTO searchUser(UserIndex userIndex, String searchString) {

        return ((UserDomainServiceImpl) AopContext.currentProxy()).searchUserEntity(userIndex, searchString).convertToUserDTO();
    }

    @Override
    public UserDTO searchUserByUserName(String userName) {

        return searchUserEntity(UserIndex.USER_NAME, userName).convertToUserDTO();
    }

    @LaborLabCacheable(keys = "User")
    @Override
    public User searchUserEntity(UserIndex userIndex, String searchString) {

        log.info("looking for an User with {} in Database", searchString);
        User user = new User();

        switch (userIndex) {

            case USER_NAME -> user = userRepository.findUserByUserName(searchString)
                                                   .orElseThrow(() -> new UserNotFoundException(searchString));
            case EMAIL -> user = userRepository.findUserByEmail(searchString)
                                               .orElseThrow(() -> new UserNotFoundException(searchString));
            case USER_ID -> user = userRepository.findById(Long.valueOf(searchString))
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
        }

        return result;
    }

    @Override
    public boolean matchPassword(long userId, String password) {

        byte[] passwordByte = this.searchUserSecurityInfo(userId).getPasswordByte();
        return encryptionService.decrypt(passwordByte).equals(password);
    }

    @LaborLabCachePut(keys = "User")
    @Override
    public UserDTO updateUser(UserDTO userDTO) {

        User user = userDTO.convertToUserEntity();
        return userRepository.save(user).convertToUserDTO();
    }

    @Override
    public boolean validateUser(UserGeneralRequestDTO userGeneralRequestDTO) {

        return false;
    }

    @LaborLabCacheEvict(keys = "User")
    @Override
    public void removeUser(UserDTO userDTO) {

        log.warn("Remove User with userName {}, email: {} in Database", userDTO.getUserName(), userDTO.getEmail());
        userRepository.deleteById(userDTO.getUserId());
    }

    @Override
    public void resetPassword(ResetUserPasswordDTO resetUserPasswordDTO) {

    }
}
