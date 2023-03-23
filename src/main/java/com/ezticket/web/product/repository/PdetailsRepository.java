package com.ezticket.web.product.repository;

import com.ezticket.web.product.pojo.Pdetails;
import com.ezticket.web.product.pojo.PdetailsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdetailsRepository extends JpaRepository<Pdetails, PdetailsPK> {


}

