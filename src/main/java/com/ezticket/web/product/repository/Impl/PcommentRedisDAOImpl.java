package com.ezticket.web.product.repository.Impl;

import com.ezticket.web.product.repository.PcommentRedisDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class PcommentRedisDAOImpl implements PcommentRedisDAO {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public Integer addKeyValue(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
        return 1;
    }

    @Override
    public Integer deleteKeyValue(String key, String value) {
        redisTemplate.opsForSet().remove(key, value);
        return 1;
    }

    @Override
    public Set findAllValues(String key) {
        return redisTemplate.opsForSet().members(key);
    }
}
