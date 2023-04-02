package idv.laborLab.userService.repository;

import idv.laborLab.userService.entity.UserSecurityInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSecurityInfoRepository extends JpaRepository<UserSecurityInfo, Long> {

    Optional<UserSecurityInfo> findUserSecurityInfoByUserId(long userId);
}
