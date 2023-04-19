package com.ezticket.web.product.repository.Impl;

import com.ezticket.web.product.repository.Top10DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class Top10DAOImpl implements Top10DAO {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public void addKeyValue(String pname, double sale_num) {
        redisTemplate.opsForZSet().incrementScore("SALE_TOP10", pname, sale_num);
    }

    @Override
    public Set<ZSetOperations.TypedTuple<String>> findAllValues() {
//        Set<String> set = redisTemplate.opsForZSet().reverseRange("SALE_TOP10", 0, 10);
        Set<ZSetOperations.TypedTuple<String>> set = redisTemplate.opsForZSet().reverseRangeWithScores("SALE_TOP10", 0, 10);
        return set;
    }
}
