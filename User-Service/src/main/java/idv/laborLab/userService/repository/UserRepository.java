package idv.laborLab.userService.repository;

import idv.laborLab.userService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUserName(String userName);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByPhoneNumber(String phoneNumber);

    boolean existsUserByEmail(String email);
    boolean existsUserByPhoneNumber(String phoneNumber);
    boolean existsUserByUserName(String userName);
}
