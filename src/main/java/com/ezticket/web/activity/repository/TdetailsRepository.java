package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.Tdetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TdetailsRepository extends JpaRepository<Tdetails,Integer> {
}
