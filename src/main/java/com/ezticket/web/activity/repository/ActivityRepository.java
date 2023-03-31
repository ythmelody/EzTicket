package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.Activity;
import com.ezticket.web.activity.pojo.Torder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository <Activity,Integer>{
//    @Query("FROM activity WHERE memberno = :id OR torderno = :id")
//    List<Torder> findByID(@Param("id") Integer id);

}
