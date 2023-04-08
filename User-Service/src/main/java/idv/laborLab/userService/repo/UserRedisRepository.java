package idv.laborLab.userService.repo;

import idv.laborLab.redisClient.repo.RedisHashRepository;
import idv.laborLab.userService.entity.User;

public interface UserRedisRepository extends RedisHashRepository<User> {
}
