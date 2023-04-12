package idv.laborLab.redisClient.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LaborLabCacheable {

    @AliasFor("cacheNames")
    String[] hashKeys() default {};

    @AliasFor("hashKeys")
    String[] cacheNames() default {};

    Class<?> returnType();
}
