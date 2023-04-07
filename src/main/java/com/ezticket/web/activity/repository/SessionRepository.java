package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.Activity;
import com.ezticket.web.activity.pojo.Session;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session,Integer> {


    List<Session> findByactivityNo(Integer activityNo);


//    Add by Shawn on 4/3
    public List<Session> findByActivityNo(Integer actNo);

    @Query("SELECT s.sessionNo FROM Session s")
    public List<Integer> getSessionNos();

}
