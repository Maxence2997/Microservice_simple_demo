package idv.laborLab.userService.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import idv.laborLab.redisClient.annotation.LaborLabCacheable;
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
    public void laborCached() {}

    @Pointcut("execution(* *..domain.UserDomainServiceImpl.searchUserEntity(..))")
    public void searchUserEntity() {}

    @Around("searchUserEntity()")
    public Object cacheUserEntity(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String searchString = aspectUtil.getParameter(proceedingJoinPoint, "searchString", String.class);
        UserIndex userIndex = aspectUtil.getParameter(proceedingJoinPoint, "userIndex", UserIndex.class);
        LaborLabCacheable laborLabCacheable = aspectUtil.getAnnotation(proceedingJoinPoint, LaborLabCacheable.class);

        log.info("looking for an User with {} in Redis cache", searchString);
        Optional<User> userOptional = Optional.empty();

        switch (userIndex) {

            case USER_NAME -> userOptional = userRedisRepository.findBy(searchString);

            case EMAIL -> userOptional = userRedisRepository.findByEmail(searchString);
        }

        if (userOptional.isPresent()) {
            log.info("Found User in Redis cache");
            return userOptional.get();
        }

        return objectMapper.convertValue(proceedingJoinPoint.proceed(), laborLabCacheable.returnType());
    }
}
