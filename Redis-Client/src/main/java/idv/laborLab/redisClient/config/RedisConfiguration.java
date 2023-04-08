package idv.laborLab.redisClient.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@EnableCaching
@Configuration
public class RedisConfiguration {

    @Value("${spring.data.redis.cacheDuration:10}")
    private int cacheDuration;

    @Bean
    public RedisConnectionFactory jedisConnectionFactory(JedisClientConfiguration jedisClientConfiguration) {

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();

        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    @Bean
    public JedisClientConfiguration jedisClientConfiguration() {

        return JedisClientConfiguration.builder()
                                       .usePooling()
                                       .build();
    }

    @Bean
    public RedisSerializer<Object> redisJsonSerializer() {

        ObjectMapper objectMapper = JsonMapper.builder()
                                              .addModule(new JavaTimeModule())
                                              .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                                              .build();
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
                                           ObjectMapper.DefaultTyping.NON_FINAL);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    @Bean
    public StringRedisSerializer redisStringSerializer() {

        return new StringRedisSerializer();
    }

    @Bean
    public RedisTemplate<String, ? extends Object> redisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                                 RedisSerializer<Object> redisJsonSerializer,
                                                                 StringRedisSerializer redisStringSerializer) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setHashKeySerializer(redisStringSerializer);
        template.setKeySerializer(redisStringSerializer);
        template.setHashValueSerializer(redisJsonSerializer);
        template.setValueSerializer(redisJsonSerializer);
        return template;
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration(StringRedisSerializer redisStringSerializer, RedisSerializer<Object> redisJsonSerializer) {

        return RedisCacheConfiguration.defaultCacheConfig()
                                      .entryTtl(Duration.ofMinutes(cacheDuration))
                                      .disableCachingNullValues()
                                      .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisStringSerializer))
                                      .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisJsonSerializer));
    }

    //    @Bean
    //    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
    //
    //        return (builder) -> builder
    //                .withCacheConfiguration("itemCache",
    //                                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
    //                .withCacheConfiguration("customerCache",
    //                                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));
    //    }
}
