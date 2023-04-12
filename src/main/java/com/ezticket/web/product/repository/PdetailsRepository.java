package com.ezticket.web.product.repository;

import com.ezticket.web.product.pojo.Pdetails;
import com.ezticket.web.product.pojo.PdetailsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PdetailsRepository extends JpaRepository<Pdetails, PdetailsPK> {
    @Query("FROM Pdetails WHERE pdetailsNo.porderno = :porderno")
    List<Pdetails> findByPorderno(@Param("porderno") Integer porderno);

}

