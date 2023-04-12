package idv.laborLab.userService.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import idv.laborLab.userService.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRedisRepositoryImpl implements UserRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    public static final String KEY = "User";

    @Override
    public void save(User user) {

        log.info("save value into redis, key: {}, hashKey: {},  value: {}",
                 KEY, user.getUserName(), user);
        this.redisTemplate.opsForHash().put(KEY, user.getEmail(), user.getUserName());
        this.redisTemplate.opsForHash().put(KEY, user.getUserName(), user);
    }

    @Override
    public Optional<User> findBy(String mappingKey) {

        return Optional.ofNullable(this.redisTemplate.opsForHash().get(KEY, mappingKey))
                       .map(object -> objectMapper.convertValue(object, User.class));   //Optional<User>
    }

    @Override
    public Optional<User> findByEmail(String email) {

        return Optional.ofNullable(this.redisTemplate.opsForHash().get(KEY, email))
                       .map(object -> objectMapper.convertValue(object, String.class))
                       .filter(StringUtils::isNotBlank)
                       .flatMap(this::findBy);
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
