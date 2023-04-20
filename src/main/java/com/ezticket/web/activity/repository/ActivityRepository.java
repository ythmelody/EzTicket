package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.Activity;
import com.ezticket.web.activity.pojo.Aimgt;
import jakarta.persistence.OrderBy;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository <Activity,Integer>{

    Optional<Activity> findByaName(String aName);
    @OrderBy("activityNo DESC")
    List<Activity> findAll();
    @OrderBy("activityNo DESC")
    List <Activity> findAllByOrderByActivityNoDesc();

    // Add by Shawn on 4/3
    @Query("SELECT act FROM Activity act WHERE CURRENT_DATE < act.aSDate ")
    List<Activity> getAllBeforeSellDate();

    @Query("SELECT act FROM Activity act WHERE act.aEDate < CURRENT_DATE")
    List<Activity> getAllAfterSellDate();

    @Query("SELECT act FROM Activity act WHERE CURRENT_DATE > act.aSDate AND act.aEDate > CURRENT_DATE")
    List<Activity> getAllBetweenSellDate();

    @Query("SELECT act FROM Activity act WHERE act.aName LIKE %?1%")
    public List<Activity> getByBlurActName(@Param("1") String actName);

    @Query("SELECT a FROM Activity a WHERE a.aEDate < :today AND a.aStatus = 1")
    List<Activity> findExpiredActivity(@Param("today") LocalDate today);

    @Query("SELECT a FROM Activity a WHERE a.aSDate >= :today AND a.aStatus = 0")
    List<Activity> findActiveActivity(@Param("today") LocalDate today);

    Optional <Activity> findByactivityNo(Integer activityNo);

    List<Activity>findAllByActivityNo(Integer activityNo);

    @Query("SELECT LAST_INSERT_ID()")
    Integer findLastInsert();

    // Update activity by id
//    public void updateActivityById(Integer activityNo, String aName, Integer aClassNo, String performer,
//                            Integer hostNo, String aDiscrip, String aNote, String aTicketRemind, String aPlace,
//                            String aPlaceAdress, Timestamp aSDate, Timestamp aEDate, Integer wetherSeat, Integer aStatus);
    @Transactional
    @Modifying
    @Query("UPDATE Activity SET aName = :aName, aClassNo = :aClassNo, performer = :performer, hostNo = :hostNo, aDiscrip = :aDiscrip, aNote = :aNote, aTicketRemind = :aTicketRemind, aPlace = :aPlace, aPlaceAdress = :aPlaceAdress, aSDate = :aSDate, aEDate = :aEDate, wetherSeat = :wetherSeat, aSeatsImg = :aSeatsImg WHERE activityNo = :activityNo")
    public void update(@Param("activityNo") Integer activityNo, @Param("aName") String aName, @Param("aClassNo") Integer aClassNo, @Param("performer") String performer, @Param("hostNo") Integer hostNo, @Param("aDiscrip") String aDiscrip, @Param("aNote") String aNote, @Param("aTicketRemind") String aTicketRemind, @Param("aPlace") String aPlace, @Param("aPlaceAdress") String aPlaceAdress, @Param("aSDate") Date aSDate, @Param("aEDate") Date aEDate, @Param("wetherSeat") Integer wetherSeat, @Param("aSeatsImg") byte[] aSeatsImg);


    @Transactional
    @Modifying
    @Query("UPDATE Activity SET aStatus = :aStatus WHERE activityNo = :activityNo")
    public void deleteActivity(@Param("activityNo") Integer activityNo, @Param("aStatus") Integer aStatus);


    //    Add by Shawn on 04/19
    @Query("SELECT act.activityNo FROM Activity act WHERE act.wetherSeat = 1")
    public List<Integer> findActNosByWetherSeatIsTrue();
}
