package idv.laborLab.sharedLibrary.tools.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AspectUtil {

    public Map<String, Object> getParameters(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        Map<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            paramMap.put(paramNames[i], args[i]);
        }
        return paramMap;
    }

    public <A extends Annotation> A getAnnotation(JoinPoint joinPoint, Class<A> annotationClass) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        return AnnotationUtils.getAnnotation(method, annotationClass);
    }

    public <T> T getParameter(JoinPoint joinpoint, String parameterName, Class<T> parameterType) {

        Map<String, Object> parameterMap = this.getParameters(joinpoint);
        Object param = Optional.ofNullable(parameterMap.getOrDefault(parameterName, null))
                               .orElseThrow(() -> new IllegalArgumentException("Can't find the parameter."));
        try {
            return parameterType.cast(param);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Can't find the parameter.");
        }
    }

    public <T> T getParameter(Map<String, Object> parameterMap, String parameterName, Class<T> parameterType) {

        Object param = Optional.ofNullable(parameterMap.getOrDefault(parameterName, null))
                               .orElseThrow(() -> new IllegalArgumentException("Can't find the parameter."));
        try {
            return parameterType.cast(param);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Can't find the parameter.");
        }
    }
}
