package com.ezticket.core.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

//驗證碼生成器
@Component
public class VerificationCodeService {

    private final RedisTemplate<String, String> redisTemplate;

    public VerificationCodeService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateCode(String email) {
        // 生成6位隨機字母數字驗證碼
        String code = RandomStringUtils.randomAlphanumeric(6);
        // 將驗證碼存儲到 Redis 中，有效期為 1 分鐘
        redisTemplate.opsForValue().set(email, code, 1, TimeUnit.MINUTES);
        return code;
    }

    public boolean validateCode(String email) {
        // 從 Redis 中取得驗證碼
        String value = redisTemplate.opsForValue().get(email);
        if (value != null) {
            // 刪除 Redis 中的驗證碼
            redisTemplate.delete(email);
            return true;
        }
        return false;
    }
}