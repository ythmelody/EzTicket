package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.CollectRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectRedisRepository extends CrudRepository<CollectRedis, String> {
}
