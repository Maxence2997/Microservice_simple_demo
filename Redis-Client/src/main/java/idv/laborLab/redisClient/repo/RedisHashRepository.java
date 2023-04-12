package idv.laborLab.redisClient.repo;

import java.util.List;
import java.util.Optional;

public interface RedisHashRepository<T> {

    void save(T mappingValue);

    Optional<T> findBy(String mappingKey);

    T update(T mappingValue);

    boolean exist(String mappingKey);

    List<T> getAll();

    void delete(String mappingKey);
}
