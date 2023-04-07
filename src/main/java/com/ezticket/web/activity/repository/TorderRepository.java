package com.ezticket.web.activity.repository;
import com.ezticket.web.activity.pojo.Activity;
import com.ezticket.web.activity.pojo.Torder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TorderRepository extends JpaRepository<Torder, Integer> {

    List <Torder> findByOrderByTorderNoDesc();

}