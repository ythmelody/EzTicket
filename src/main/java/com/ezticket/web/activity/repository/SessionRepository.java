package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session,Integer> {

//    Add by Shawn on 4/3
    public List<Session> findByActivityNo(Integer actNo);

    @Query("SELECT s.sessionNo FROM Session s")
    public List<Integer> getSessionNos();

}
