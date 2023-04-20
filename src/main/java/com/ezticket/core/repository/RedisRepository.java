package com.ezticket.core.repository;

import com.ezticket.core.pojo.RedisData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends JpaRepository<RedisData, String> {
     //JPA裡有預設方法 (不太熟名稱紀錄一下)
    // saveRedisData(k,v)  /  getRedisData(k) / deleteRedisData(k)


}
