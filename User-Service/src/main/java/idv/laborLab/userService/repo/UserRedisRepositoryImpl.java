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
    public Optional<User> findBy(String hashKey) {

        return Optional.ofNullable(this.redisTemplate.opsForHash().get(KEY, hashKey))
                       .map(object -> objectMapper.convertValue(object, User.class));   //Optional<User>
    }

    @Override
    public Optional<User> findByEmail(String email) {

        return this.findUserNameByEmail(email)
                   .flatMap(this::findByUserName);
    }

    @Override
    public Optional<User> findByUserName(String userName) {

        return this.findBy(userName);
    }

    @Override
    public void delete(User user) {

        this.deleteBy(user.getEmail());
        this.deleteBy(user.getUserName());
    }

    @Override
    public void deleteByUserName(String userName) {

        this.findByUserName(userName).ifPresent(user -> {this.deleteBy(user.getEmail()); this.deleteBy(user.getUserName());});
    }

    @Override
    public void deleteByEmail(String email) {

        this.findUserNameByEmail(email).ifPresentOrElse(userName -> {this.deleteBy(userName); this.deleteBy(email);}, () -> this.deleteBy(email));
    }

    @Override
    public void update(User user) {

        // if user data present then delete it
        this.findBy(user.getUserName()).ifPresent(this::delete);

        // save the new one
        this.save(user);
    }

    @Override
    public boolean exist(String hashKey) {

        return false;
    }

    @Override
    public List<User> getAll() {

        return null;
    }

    @Override
    public void deleteBy(String hashKey) {

        this.redisTemplate.opsForHash().delete(KEY, hashKey);
    }

    private Optional<String> findUserNameByEmail(String email) {

        return Optional.ofNullable(this.redisTemplate.opsForHash().get(KEY, email))
                       .map(userName -> objectMapper.convertValue(userName, String.class))
                       .filter(StringUtils::isNotBlank);
    }
}
