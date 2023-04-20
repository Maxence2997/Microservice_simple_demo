package idv.laborLab.miscellaneous.request;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class HttpServletRequestUtils implements RequestRecordable {

    private final HttpServletRequest httpServletRequest;
    public static final String X_FORWARDED_FOR = "X-FORWARDED-FOR";

    @Override
    public String getClientIP() {

        String remoteAddr = "";
        HttpServletRequest request;

        try {
            request = httpServletRequest;
            remoteAddr = request.getHeader(X_FORWARDED_FOR);
        } catch (IllegalStateException illegalStateException) {

            log.warn(illegalStateException.getMessage());
            request = getSharedRequest();
            remoteAddr = request.getHeader(X_FORWARDED_FOR);
        }

        if (remoteAddr == null || StringUtils.isEmpty(remoteAddr)) {
            remoteAddr = request.getRemoteAddr();
        }
        return remoteAddr;
    }

    @Override
    public Map<String, String> getRequestHeadersInMap() {

        Map<String, String> result = new HashMap<>();
        HttpServletRequest request;
        Enumeration<String> headerNames;

        try {
            request = httpServletRequest;
            headerNames = request.getHeaderNames();
        } catch (IllegalStateException illegalStateException) {

            log.warn(illegalStateException.getMessage());
            headerNames = getSharedRequest().getHeaderNames();
        }

        while (headerNames.hasMoreElements()) {

            String key = headerNames.nextElement();
            String value = httpServletRequest.getHeader(key);
            result.put(key, value);
        }
        return result;
    }

    private HttpServletRequest getSharedRequest() {

        HttpServletRequest request = null;
        try {
            RequestAttributes attributes =
                    Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                            .stream()
                            .peek(r -> {
                                log.info("attributes class: {}", r.getClass().toGenericString());
                            })
                            .findAny()
                            .orElseGet(() -> {
                                log.warn("RequestAttributes is null");
                                return null;
                            });

            if (attributes instanceof NativeWebRequest nativeWebRequest) {
                log.info("Request is native web request");
                request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
            }
        } catch (Exception e) {
            log.error("Error happened while getting sharedRequest");
            // #TODO: sent to ExecutingRecord DB
        }
        return request;
    }
}
