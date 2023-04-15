package idv.laborLab.userService.repo;

import idv.laborLab.redisClient.repo.RedisHashRepository;
import idv.laborLab.userService.entity.User;

import java.util.Optional;

/**
 * Storing data structure of User entity in Redis Cache <br>
 * <p>
 * key --> value <br>
 * userId --> userName <br>
 * userEmail --> userName <br>
 * userName  --> user data
 */
public interface UserRedisRepository extends RedisHashRepository<User> {

    Optional<User> findByUserId(String userIdString);
    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String userName);

    void delete(User user);

    void deleteByUserName(String userName);

    void deleteByEmail(String email);
}
