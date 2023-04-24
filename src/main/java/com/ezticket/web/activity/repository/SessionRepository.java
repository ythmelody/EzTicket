package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.Activity;
import com.ezticket.web.activity.pojo.Session;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import java.util.List;
import java.util.Set;

@Repository
public interface SessionRepository extends JpaRepository<Session,Integer> {


    List<Session> findByactivityNo(Integer activityNo);


    //    Add by Shawn on 4/3
    public List<Session> findByActivityNo(Integer actNo);

    @Query("SELECT s.sessionNo FROM Session s JOIN Activity act WHERE act.aEDate > current_date")
    public Set<Integer> getToSellSessions();

    //    Add by Shawn on 04/08
    @Transactional
    @Modifying
    @Query("UPDATE Session SET standingQty = standingQty + :ticketChange where sessionNo = :sessionNo")
    public int updateById(@Param("ticketChange") Integer ticketChange, @Param("sessionNo") Integer sessionNo);



    @Transactional
    @Modifying
    @Query("UPDATE Session SET sessionNo = :sessionNo, sessionsTime = :sessionsTime, sessioneTime = :sessioneTime,maxSeatsQty=:maxSeatsQty,maxStandingQty=:maxStandingQty where sessionNo = :sessionNo")
    public void update(@Param("sessionNo") Integer sessionNo,Session session);


    //    Add by Shawn on 04/08
    @Transactional
    @Modifying
    @Query("UPDATE Session SET standingQty = standingQty + :ticketChange where sessionNo = :sessionNo")
    public int updateStandingQtyById(@Param("ticketChange") Integer ticketChange, @Param("sessionNo") Integer sessionNo);

    //    Add by Shawn on 04/22
    @Query("SELECT (s.maxStandingQty - s.standingQty) FROM Session s WHERE s.sessionNo = :sessionNo")
    public int getToSellNumber(@Param("sessionNo") int sessionNo);



}
