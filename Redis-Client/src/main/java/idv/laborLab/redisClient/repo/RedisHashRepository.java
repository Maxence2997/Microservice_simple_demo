package idv.laborLab.redisClient.repo;

import java.util.List;

public interface RedisHashRepository<T> {

    void save(T mappingValue);

    T findBy(String mappingKey);

    T update(T mappingValue);

    boolean exist(String mappingKey);

    List<T> getAll();

    void delete(String mappingKey);
}
