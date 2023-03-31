package idv.laborLab.userService.dao;

import idv.laborLab.userService.entity.User;
import idv.laborLab.userService.entity.UserSecurityInfo;

public interface UserDaoService {

    long saveUserIntoDatabase(User user);
    long saveUserSecurityInfoIntoDatabase(UserSecurityInfo userSecurityInfo);
}
