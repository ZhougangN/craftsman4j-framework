package com.slipper4j.framework.limiter.core;

import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author andanyang
 * @since 2023/5/12 9:21
 */
public interface RateLimiterKeyGenerate {

    void generateKey(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, StringBuilder keyBuilder);
}
