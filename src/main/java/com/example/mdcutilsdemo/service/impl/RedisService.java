package com.example.mdcutilsdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置缓存值（无过期时间）
     * @param key 键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置缓存值并指定过期时间
     * @param key 键
     * @param value 值
     * @param timeout 过期时间
     * @param unit 时间单位
     */
    public void setWithExpire(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取缓存值
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存键
     * @param key 键
     * @return 是否删除成功
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 设置过期时间
     * @param key 键
     * @param timeout 过期时间
     * @param unit 时间单位
     * @return 是否设置成功
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 判断键是否存在
     * @param key 键
     * @return 是否存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取剩余过期时间
     * @param key 键
     * @return 剩余时间（秒）
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 分布式锁 - 获取锁
     * @param lockKey 锁键
     * @param value 锁值
     * @param expireTime 过期时间（秒）
     * @return 是否获取成功
     */
    public Boolean tryLock(String lockKey, String value, long expireTime) {
        return redisTemplate.opsForValue().setIfAbsent(lockKey, value, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 分布式锁 - 释放锁
     * @param lockKey 锁键
     */
    public void releaseLock(String lockKey) {
        redisTemplate.delete(lockKey);
    }

    /**
     * 生成全局唯一ID
     * @param keyPrefix 业务前缀（如："order"、"user"）
     * @return 全局唯一ID（格式：前缀 + 时间戳 + 序列号）
     */
    public String generateId(String keyPrefix) {
        // 获取当前时间戳（秒级）
        long timestamp = System.currentTimeMillis() / 1000;

        // 获取日期字符串（用于每日重置序列）
        String dateStr = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        // 构造Redis计数器key
        String counterKey = "id_generator:" + keyPrefix + ":" + dateStr;

        // 原子递增获取序列号
        Long sequence = redisTemplate.opsForValue().increment(counterKey, 1);

        // 如果是当天首次生成，设置过期时间为24小时
        if (sequence == 1) {
            redisTemplate.expire(counterKey, 25, TimeUnit.HOURS);
        }

        // 组合ID：前缀 + 时间戳 + 序列号（补齐6位）
        return keyPrefix  + timestamp  + String.format("%06d", sequence);
    }

    // 在 RedisService 类中添加以下方法
    /**
     * 批量生成全局唯一ID（优化版）
     * @param keyPrefix 业务前缀（如："order"、"user"）
     * @param count 生成数量（1-10000）
     * @return 全局唯一ID列表（格式：前缀 + 时间戳 + 序列号）
     */
    public List<String> batchGenerateIds(String keyPrefix, int count) {
        // 参数校验
        if (count <= 0 || count > 1000000) {
            throw new IllegalArgumentException("生成数量必须在 1-1000000 之间");
        }

        // 获取当前时间戳（毫秒级）
        long timestamp = System.currentTimeMillis();

        // 获取日期字符串（用于每日重置序列）
        String dateStr = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        // 构造Redis计数器key
        String counterKey = "id_generator:" + keyPrefix + ":" + dateStr;

        // 原子递增获取序列号范围
        Long firstSequence = redisTemplate.opsForValue().increment(counterKey, count);

        // 如果计数器不存在，设置过期时间
        if (firstSequence == count) {
            redisTemplate.expire(counterKey, 25, TimeUnit.HOURS);
        }

        // 生成ID列表
        List<String> ids = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            long sequence = firstSequence - count + i;
            ids.add(String.format("%s%d%08d", keyPrefix, timestamp, sequence));
        }

        return ids;
    }

    /**
     * 带机器标识的批量生成ID（分布式环境使用）
     * @param keyPrefix 业务前缀
     * @param count 生成数量
     * @param nodeId 机器标识（01-99）
     * @return 全局唯一ID列表
     */
    public List<String> batchGenerateIdsWithNode(String keyPrefix, int count, String nodeId) {
        List<String> baseIds = batchGenerateIds(keyPrefix, count);
        List<String> result = new ArrayList<>(count);

        for (String id : baseIds) {
            // 在时间戳后插入机器标识
            String[] parts = id.split("_");
            result.add(parts[0] +  parts[1]  + nodeId  + parts[2]);
        }

        return result;
    }


}
