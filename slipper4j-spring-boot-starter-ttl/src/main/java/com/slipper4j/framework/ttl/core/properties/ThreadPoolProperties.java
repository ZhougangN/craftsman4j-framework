package com.slipper4j.framework.ttl.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author andanyang
 * @since 2022/11/4 16:02
 */
@ConfigurationProperties("slipper4j.async")
@Data
public class ThreadPoolProperties {
    /**
     * 线程池维护线程的最小数量.
     */
    private int corePoolSize = 10;

    /**
     * 线程池维护线程的最大数量
     */
    private int maxPoolSize = 200;

    /**
     * 队列最大长度
     */
    private int queueCapacity = 200;

    /**
     * 线程池前缀
     */
    private String threadNamePrefix = "Slipper4jExecutor-";
}
