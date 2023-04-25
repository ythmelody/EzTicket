package com.ezticket.web.product.repository.Impl;

import com.ezticket.web.product.repository.Top10DAO;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class Top10DAOImpl implements Top10DAO {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @PersistenceContext
    private Session session;

    @Override
    public void addKeyValue(String pname, double sale_num) {
        redisTemplate.opsForZSet().incrementScore("SALE_TOP10", pname, sale_num);
    }

    @Override
    public Set<ZSetOperations.TypedTuple<String>> findAllValues() {
//        Set<String> set = redisTemplate.opsForZSet().reverseRange("SALE_TOP10", 0, 10);
        Set<ZSetOperations.TypedTuple<String>> set = redisTemplate.opsForZSet().reverseRangeWithScores("SALE_TOP10", 0, 7);
        return set;
    }

    @Override
    public List<Object[]> search(String keyword, int pageNum, int pageSize) {
        final String query = "SELECT pname AS name, productno AS no, 'product' AS type FROM Product " +
                "WHERE pname LIKE :pname " +
                "UNION " +
                "SELECT aName AS name, activityNo AS no, 'activity' AS type FROM Activity " +
                "WHERE aName LIKE :aName " +
                "LIMIT :limit OFFSET :offset";

        Query nativeQuery = session.createNativeQuery(query, Object[].class);
        nativeQuery.setParameter("pname", "%" + keyword + "%");
        nativeQuery.setParameter("aName", "%" + keyword + "%");
        nativeQuery.setParameter("limit", pageSize);
        nativeQuery.setParameter("offset", (pageNum - 1) * pageSize);
        List<Object[]> results = nativeQuery.getResultList();
//        for (Object[] result: results) {
//            System.out.println(result[0]+", "+result[1]+", "+result[2]);
//        }
        return results;
    }
}
