package com.ezticket.web.product.repository;

import com.ezticket.web.product.pojo.Pcouponholding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PcouponholdingRepository extends JpaRepository<Pcouponholding, Integer> {
    @Query("FROM Pcouponholding WHERE pcouponholdingPK.memberno = :memberno")
    List<Pcouponholding> findByMemberno(@Param("memberno")Integer memberno);
}

