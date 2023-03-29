package com.ezticket.web.product.repository;

import com.ezticket.web.product.pojo.Porder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Porder, Integer> {

}

