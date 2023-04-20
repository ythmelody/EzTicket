package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.Collect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectRepository extends JpaRepository<Collect, Integer> {
    public Collect findByTdetailsno(Integer tdetailsno);
}
