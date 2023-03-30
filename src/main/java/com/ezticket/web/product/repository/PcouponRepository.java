package com.ezticket.web.product.repository;

import com.ezticket.web.product.pojo.Pcoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PcouponRepository extends JpaRepository<Pcoupon, Integer> {


}

