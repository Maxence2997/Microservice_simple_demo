package idv.laborLab.redisClient.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;

@Repository
public class RedisGeneralValueRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final ValueOperations<String, Object> valueOperations;

    @Autowired
    public RedisGeneralValueRepository(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {

        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.valueOperations = redisTemplate.opsForValue();
    }

    public <R> void save(String hashKey, R mappingValue) {

        valueOperations.set(hashKey, mappingValue, Duration.ofSeconds(30));
    }

    public <R> R findBy(String hashKey, Class<R> clazz) {

        return objectMapper.convertValue(valueOperations.get(hashKey), clazz);
    }

    public <R> List<R> getAll(String hashKey, Class<R> c) {

        return null;
    }

    public void delete(Object id, String hashKey) {

    }
}
