package com.slipper4j.framework.limiter.redis;

import com.slipper4j.framework.limiter.core.constant.LimiterType;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 固定窗口，限流
 *
 * @author andanyang
 * @since 2023/5/11 13:39
 */
public class TokenBucketRedisRateLimiter extends AbstractApiRedisRateLimiter {

    public TokenBucketRedisRateLimiter(StringRedisTemplate stringRedisTemplate) {
        super(stringRedisTemplate);
    }

    @Override
    public LimiterType support() {
        return LimiterType.TOKEN_BUCKET;
    }

    @Override
    protected String getScriptName() {
        return "rateLimiter/TokenBucket.lua";
    }
}