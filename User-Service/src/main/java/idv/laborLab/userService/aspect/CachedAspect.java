package idv.laborLab.userService.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import idv.laborLab.sharedLibrary.tools.aspect.AspectUtil;
import idv.laborLab.userService.dto.UserIndex;
import idv.laborLab.userService.entity.User;
import idv.laborLab.userService.repo.UserRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
@Aspect
@Order(5)
@Slf4j
public class CachedAspect {

    private final AspectUtil aspectUtil;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final UserRedisRepository userRedisRepository;

    public CachedAspect(AspectUtil aspectUtil, RedisTemplate<String, Object> redisTemplate,
                        ObjectMapper objectMapper, UserRedisRepository userRedisRepository) {

        this.aspectUtil = aspectUtil;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.userRedisRepository = userRedisRepository;
    }

    @Pointcut("@annotation(idv.laborLab.redisClient.annotation.LaborLabCacheable)")
    public void cacheable() {}

    @Pointcut("execution(* *..domain.UserDomainServiceImpl.searchUserEntity(..))")
    public void searchUserEntity() {}

    @Around("searchUserEntity() && cacheable()")
    public Object cacheUserEntity(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String searchString = aspectUtil.getParameter(proceedingJoinPoint, "searchString", String.class);
        UserIndex userIndex = aspectUtil.getParameter(proceedingJoinPoint, "userIndex", UserIndex.class);

        log.info("looking for an User with {} in Redis cache", searchString);
        Optional<User> userOptional = Optional.empty();

        switch (userIndex) {

            case USER_NAME -> userOptional = userRedisRepository.findBy(searchString);

            case EMAIL -> userOptional = userRedisRepository.findByEmail(searchString);
        }

        if (userOptional.isPresent()) {
            log.info("Found User {} in Redis cache", userOptional.get());
            return userOptional.get();
        }
        User user = objectMapper.convertValue(proceedingJoinPoint.proceed(), User.class);

        // write it into User cache
        CompletableFuture.runAsync(() -> userRedisRepository.save(user));

        return user;
    }
}
