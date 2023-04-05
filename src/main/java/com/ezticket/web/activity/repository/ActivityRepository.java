package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.dto.ActivityDto;
import com.ezticket.web.activity.pojo.AComment;
import com.ezticket.web.activity.pojo.Activity;
import com.ezticket.web.activity.pojo.Torder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository <Activity,Integer>{

    Optional<Activity> findByaName(String aName);

    // Add by Shawn on 4/3
    @Query("SELECT act FROM Activity act WHERE CURRENT_DATE < act.aSDate ")
    List<Activity> getAllBeforeSellDate();

    @Query("SELECT act FROM Activity act WHERE act.aEDate < CURRENT_DATE")
    List<Activity> getAllAfterSellDate();

    @Query("SELECT act FROM Activity act WHERE CURRENT_DATE > act.aSDate AND act.aEDate > CURRENT_DATE")
    List<Activity> getAllBetweenSellDate();

    @Query("SELECT act FROM Activity act WHERE act.aName LIKE %?1%")
    public List<Activity> getByBlurActName(@Param("1") String actName);

}
