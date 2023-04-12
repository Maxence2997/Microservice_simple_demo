package idv.laborLab.userService.repo;

import idv.laborLab.redisClient.repo.RedisHashRepository;
import idv.laborLab.userService.entity.User;

import java.util.Optional;

public interface UserRedisRepository extends RedisHashRepository<User> {

    Optional<User> findByEmail(String email);
}
