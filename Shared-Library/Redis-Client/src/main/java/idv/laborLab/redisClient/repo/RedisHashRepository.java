package idv.laborLab.redisClient.repo;

import java.util.List;
import java.util.Optional;

public interface RedisHashRepository<T> {

    void save(T object);

    Optional<T> findBy(String hashKey);

    void update(T object);

    boolean exist(String hashKey);

    List<T> getAll();

    void deleteBy(String hashKey);
}
