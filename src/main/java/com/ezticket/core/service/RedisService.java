package com.ezticket.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    //檢查驗證碼是否符合
    public boolean checkCode(String email, String code) {
        String key = email;
        String storedCode = redisTemplate.opsForValue().get(key);
        if (storedCode == null) {
            return false;
        }
        if (storedCode.equals(code)) {
//            redisTemplate.delete(key);
            return true;
        }
        return false;
    }
}