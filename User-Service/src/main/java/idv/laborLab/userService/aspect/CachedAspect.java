package idv.laborLab.userService.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import idv.laborLab.sharedLibrary.miscellaneous.aspect.AspectUtil;
import idv.laborLab.userService.dto.UserDTO;
import idv.laborLab.userService.dto.UserIndex;
import idv.laborLab.userService.entity.User;
import idv.laborLab.userService.repo.UserRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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

    @Pointcut("@annotation(idv.laborLab.redisClient.annotation.LaborLabCacheEvict)")
    public void cacheEvict() {}

    @Pointcut("@annotation(idv.laborLab.redisClient.annotation.LaborLabCachePut)")
    public void cachePut() {}

    @Pointcut("execution(* *..domain.UserDomainServiceImpl.searchUserEntity(..))")
    public void searchUserEntity() {}

    @Pointcut("execution(* *..domain.UserDomainServiceImpl.removeUser(..))")
    public void removeUserEntity() {}

    @Pointcut("execution(* *..domain.UserDomainServiceImpl.updateUser(..))")
    public void updateUserEntity() {}

    @Around("searchUserEntity() && cacheable()")
    public User cacheUserEntity(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String searchString = aspectUtil.getParameter(proceedingJoinPoint, "searchString", String.class);
        UserIndex userIndex = aspectUtil.getParameter(proceedingJoinPoint, "userIndex", UserIndex.class);

        log.info("looking for an User with {} in Redis cache", searchString);
        Optional<User> userOptional = Optional.empty();

        switch (userIndex) {

            case USER_NAME -> userOptional = userRedisRepository.findByUserName(searchString);
            case EMAIL -> userOptional = userRedisRepository.findByEmail(searchString);
            case USER_ID -> userOptional = userRedisRepository.findByUserId(searchString);
        }

        if (userOptional.isPresent()) {
            log.info("Found user in Redis cache, {}", userOptional.get());
            return userOptional.get();
        }

        User user = objectMapper.convertValue(proceedingJoinPoint.proceed(), User.class);

        // write it into User cache
        CompletableFuture.runAsync(() -> userRedisRepository.save(user));

        return user;
    }

    @AfterReturning("removeUserEntity() && cacheEvict()")
    public void cacheEvictUserEntity(JoinPoint joinPoint) {

        UserDTO userDTO = aspectUtil.getParameter(joinPoint, "userDTO", UserDTO.class);

        log.info("Evict an User with userName {}, email {} in Redis cache", userDTO.getUserName(), userDTO.getEmail());

        CompletableFuture.runAsync(() -> userRedisRepository.delete(userDTO.convertToUserEntity()));
    }

    @AfterReturning("updateUserEntity() && cachePut()")
    public void updateUserEntity(JoinPoint joinPoint) {

        UserDTO userDTO = aspectUtil.getParameter(joinPoint, "userDTO", UserDTO.class);

        log.info("update user with userName {}, email {} in Redis cache", userDTO.getUserName(), userDTO.getEmail());

        CompletableFuture.runAsync(() -> userRedisRepository.update(userDTO.convertToUserEntity()));
    }
}
