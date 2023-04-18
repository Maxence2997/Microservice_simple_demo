package idv.laborLab.userService.domain;

import idv.laborLab.sharedLibrary.miscellaneous.services.IDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisIDService implements IDService {

    private final RedisAtomicLong atomicLong;

    @Autowired
    public RedisIDService(RedisTemplate<String, Long> redisStringKeyTemplate, String idCounterKey) {

        this.atomicLong = new RedisAtomicLong(idCounterKey, redisStringKeyTemplate);
    }

    @Override
    public long getNextID() {

        return atomicLong.incrementAndGet();
    }
}
