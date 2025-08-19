package com.wj.demo.framework.redis.service;

import com.wj.demo.framework.exception.exception.BusinessException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author wj
 * @version 1.0
 * @Desc redis操作工具类 存储数据的类建议序列化
 * @date 2024/4/30 16:53
 */
@Component
@Slf4j
public class RedisClient {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取所有key
     * @param pattern 格式
     * @return 值
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public Boolean expire(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 分布式锁
     *
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> Boolean setnx(String key, T value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public <T> T get(String key) {
        return key == null ? null : (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public <T> void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间
     * @param unit  时间单位
     */
    public <T> void set(String key, T value, Long time, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, time, unit);
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     */
    public Long increment(String key, long delta) {
        if (delta < 0) {
            throw new BusinessException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     */
    public Long decrement(String key, long delta) {
        if (delta < 0) {
            throw new BusinessException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().decrement(key, -delta);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key     键
     * @param hashKey 项
     * @param value   值
     */
    public <T> void hashPut(String key, String hashKey, T value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param value 值
     */
    public <T> void hashPutAll(String key, Map<String, T> value) {
        redisTemplate.opsForHash().putAll(key, value);
    }

    /**
     * HashGet
     *
     * @param key     键 不能为null
     * @param hashKey 项 不能为null
     * @return 值
     */
    public <T> T hashGet(String key, String hashKey) {
        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * HashGet
     *
     * @param key      键 不能为null
     * @param hashKeys 项 不能为null
     * @return 值
     */
    public <T> Map<String, T> hashMultiGet(String key, String... hashKeys) {
        Map<String, T> result = new HashMap<>();
        for (String hashKey : hashKeys) {
            result.put(hashKey, this.hashGet(key, hashKey));
        }
        return result;
    }

    /**
     * 获取hash表中所有值
     *
     * @param pattern 格式
     * @return 值
     */
    public <T> Map<String, Map<String, T>> hashGetAll(String pattern) {
        // 获取hash操作对象
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        // 返回结果
        Map<String, Map<String, T>> result = new HashMap<>();
        // 按照格式匹配所有key
        Set<String> keys = redisTemplate.keys(pattern);
        for (String key : keys) {
            result.put(key, opsForHash.entries(key));
        }
        return result;
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public <T> Long hashRemove(String key, T... item) {
        return redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     */
    public <T> Set<T> setGet(String key) {
        return (Set<T>) redisTemplate.opsForSet().members(key);
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     *               return 成功个数
     */
    public <T> Long setSet(String key, T... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     *               return 移除的个数
     */
    public <T> Long removeSet(String key, T... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public <T> Long rightPush(String key, T value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public <T> Long rightPushAll(String key, List<T> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1 代表所有值
     */
    public <T> List<T> getList(String key, long start, long end) {
        return (List<T>) redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public <T> Long removeListValue(String key, long count, T value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }


}
