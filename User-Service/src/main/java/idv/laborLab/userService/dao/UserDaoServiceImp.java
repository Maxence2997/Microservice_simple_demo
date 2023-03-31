package idv.laborLab.userService.dao;

import idv.laborLab.userService.entity.User;
import idv.laborLab.userService.entity.UserSecurityInfo;
import idv.laborLab.userService.repository.UserRepository;
import idv.laborLab.userService.repository.UserSecurityInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDaoServiceImp implements UserDaoService{

    private final UserRepository userRepository;
    private final UserSecurityInfoRepository userSecurityInfoRepository;
    @Override
    public long saveUserIntoDatabase(User user) {

        return userRepository.save(user).getId();
    }

    @Override
    public long saveUserSecurityInfoIntoDatabase(UserSecurityInfo userSecurityInfo) {

        //encrypt it here
        return userSecurityInfoRepository.save(userSecurityInfo).getId();
    }
}
