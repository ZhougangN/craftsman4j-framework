package com.craftsman4j.framework.limiter.core.key;

import com.craftsman4j.framework.limiter.core.RateLimiterKeyGenerate;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhougang
 * @since 2023/5/12 10:01
 */
public class DefaultRateLimiterKeyGenerate implements RateLimiterKeyGenerate {

    @Override
    public void generateKey(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, StringBuilder keyBuilder) {
        keyBuilder.append(request.getRequestURI());
    }
}
