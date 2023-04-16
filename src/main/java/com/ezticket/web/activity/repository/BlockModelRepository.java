package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.BlockModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockModelRepository extends JpaRepository<BlockModel, Integer> {
    <List>BlockModel findByModelno(Integer modelno);
}
