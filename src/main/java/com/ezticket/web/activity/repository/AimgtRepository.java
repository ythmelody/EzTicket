package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.Aimgt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AimgtRepository extends JpaRepository <Aimgt,Integer> {
}
