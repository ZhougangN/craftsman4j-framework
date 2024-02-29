package com.craftsman4j.framework.redis.core.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 简单的工具类
 * https://www.runoob.com/redis/redis-strings.html
 **/
@Getter
@RequiredArgsConstructor
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class RedisUtils {

    private static RedisTemplate redisTemplate;

    public static void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public static <T> void set(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Redis Get 命令用于获取指定 key 的值。如果 key 不存在，返回 nil
     */
    public static <T> T get(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * Redis Getrange 命令用于获取存储在指定 key 中字符串的子字符串。字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。
     */
    public static String getRange(final String key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * Redis Getset 命令用于设置指定 key 的值，并返回 key 的旧值。
     */
    public static <T> T getSet(final String key, T value) {
        ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
        return valueOperations.getAndSet(key, value);
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 seconds (以秒为单位)。
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public static <T> void setEx(final String key, final T value, final Long timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 只有在 key 不存在时设置 key 的值。
     */
    public static <V> Boolean setNx(final String key, final V value) {
        ValueOperations<String, V> valueOperations = redisTemplate.opsForValue();
        return valueOperations.setIfAbsent(key, value);
    }

    public static <V> Boolean setNx(final String key, final V value, long timeout, TimeUnit unit) {
        ValueOperations<String, V> valueOperations = redisTemplate.opsForValue();
        return valueOperations.setIfAbsent(key, value, timeout, unit);
    }

    /**
     * 自增
     */
    public static <V> Long increment(final String key) {
        ValueOperations<String, V> valueOperations = redisTemplate.opsForValue();
        return valueOperations.increment(key);
    }

    /**
     * 自增
     */
    public static <V> Long increment(final String key, long delta) {
        ValueOperations<String, V> valueOperations = redisTemplate.opsForValue();
        return valueOperations.increment(key, delta);
    }

    public static <V> Double increment(final String key, double delta) {
        ValueOperations<String, V> valueOperations = redisTemplate.opsForValue();
        return valueOperations.increment(key, delta);
    }

    /**
     * 自增
     */
    public static <V> Long decrement(final String key) {
        ValueOperations<String, V> valueOperations = redisTemplate.opsForValue();
        return valueOperations.decrement(key);
    }

    /**
     * 自增
     */
    public static <V> Long decrement(final String key, long delta) {
        ValueOperations<String, V> valueOperations = redisTemplate.opsForValue();
        return valueOperations.decrement(key, delta);
    }


    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间。单位秒
     * @return true=设置成功；false=设置失败
     */
    public static boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public static boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }


    /**
     * 删除单个对象
     *
     * @param key
     */
    public static boolean delete(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     */
    public static long delete(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public static <T> long setList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public static <V> List<V> getList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public static <T> BoundSetOperations<String, T> setSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     */
    public static <T> Set<T> getSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public static <T> void setMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     */
    public static <T> Map<String, T> getMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public static <T> void setMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public static <T> T getMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * hash 自增
     *
     * @param key
     * @param hKey
     * @param <T>
     * @return
     */
    public static <T> Long increment(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.increment(key, hKey, 1);
    }

    public static <T> Long increment(final String key, final String hKey, final Long delta) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.increment(key, hKey, delta);
    }

    public static <T> Double increment(final String key, final String hKey, final double delta) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.increment(key, hKey, delta);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public static <T> List<T> getMultiMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public static Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

}
