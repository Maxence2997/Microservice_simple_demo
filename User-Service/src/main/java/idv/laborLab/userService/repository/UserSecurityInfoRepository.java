package idv.laborLab.userService.repository;

import idv.laborLab.userService.entity.UserSecurityInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSecurityInfoRepository extends JpaRepository<UserSecurityInfo, Long> {
}
