package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.Tdetails;
import com.ezticket.web.product.pojo.Pdetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TdetailsRepository extends JpaRepository<Tdetails,Integer> {

//    Add by Shawn on 04/17
    @Query("SELECT td FROM Tdetails td WHERE td.torderNo = :torderno")
    List<Tdetails> findByTorderno(@Param("torderno") Integer torderno);

    Tdetails findByTorderNo(Integer torderNo);
}
