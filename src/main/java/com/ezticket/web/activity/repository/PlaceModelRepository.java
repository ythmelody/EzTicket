package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.PlaceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceModelRepository extends JpaRepository<PlaceModel, Integer> {

}
