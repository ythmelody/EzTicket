package com.ezticket.web.product.repository;

import com.ezticket.web.product.pojo.Pfitcoupon;
import com.ezticket.web.product.pojo.PfitcouponPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PfitcouponRepository extends JpaRepository<Pfitcoupon, PfitcouponPK> {
    @Query("FROM Pfitcoupon WHERE pfitcouponNo.pcouponno = :pcouponno")
    Pfitcoupon findByPcouponno(@Param("pcouponno") Integer pcouponno);
}
