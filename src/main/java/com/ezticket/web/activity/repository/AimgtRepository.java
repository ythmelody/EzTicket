package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.Activity;
import com.ezticket.web.activity.pojo.Aimgt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AimgtRepository extends JpaRepository <Aimgt,Integer> {
    List<Aimgt> findByActivityNo(Integer activityNo);
    List<Aimgt>findAllByActivityNo(Integer activityNo);
}
