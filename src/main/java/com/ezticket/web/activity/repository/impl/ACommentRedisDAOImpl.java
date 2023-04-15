package com.ezticket.web.activity.repository.impl;

import com.ezticket.web.activity.repository.ACommentRedisDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class ACommentRedisDAOImpl implements ACommentRedisDAO<String> {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public int setAddKV(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
        return 1;
    }

    @Override
    public int setDelKV(String key, String value) {
        redisTemplate.opsForSet().remove(key, value);
        return 1;
    }

    @Override
    public Set<String> setFindAllValues(String key) {
        return redisTemplate.opsForSet().members(key);
    }
}
