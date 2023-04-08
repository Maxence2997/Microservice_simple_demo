package idv.laborLab.userService.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import idv.laborLab.userService.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRedisRepositoryImpl implements UserRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    public static final String KEY = "User";

    @Override
    public void save(User user) {

        log.info("save value into redis,key: {}, hashKey: {},  value: {}", user.getClass().getSimpleName(), user.getUserName(), user);
        this.redisTemplate.opsForHash().put(KEY, user.getUserName(), user);
    }

    @Override
    public User findBy(String mappingKey) {

        return objectMapper.convertValue(this.redisTemplate.opsForHash().get(User.class.getSimpleName(), mappingKey), User.class);
    }

    @Override
    public User update(User mappingValue) {

        return null;
    }

    @Override
    public boolean exist(String mappingKey) {

        return false;
    }

    @Override
    public List<User> getAll() {

        return null;
    }

    @Override
    public void delete(String mappingKey) {

    }
}
