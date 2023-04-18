package idv.laborLab.redisClient.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RedisGeneralHashRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final HashOperations<String, String, Object> hashOperation;

    @Autowired
    public RedisGeneralHashRepository(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {

        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.hashOperation = this.redisTemplate.opsForHash();
    }

    public <R> void save(String hashKey, String mappingKey, R mappingValue) {

        this.hashOperation.put(hashKey, mappingKey, mappingValue);
    }

    public <R> R findBy(String hashKey, String mappingKey, Class<R> clazz) {

        return objectMapper.convertValue(this.hashOperation.get(mappingKey, hashKey), clazz);
    }

    public <R> List<R> getAll(String hashKey, Class<R> c) {

        return null;
    }

    public void delete(Object id, String hashKey) {

    }
}
