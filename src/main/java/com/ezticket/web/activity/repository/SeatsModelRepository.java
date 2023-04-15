package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.SeatsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatsModelRepository extends JpaRepository<SeatsModel, Integer> {
}
