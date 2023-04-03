package com.ezticket.web.product.repository;

import com.ezticket.web.product.pojo.Pcouponholding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PcouponholdingRepository extends JpaRepository<Pcouponholding, Integer> {
//    List<Pcouponholding> findByMemberno(Integer memberno);
}

