package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.SeatsModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatsModelRepository extends JpaRepository<SeatsModel, Integer> {
    public List<SeatsModel> findByBlocknoOrderByXAscYAsc(Integer blockno);

    @Modifying
    @Transactional
    public int deleteByBlockno(Integer blockno);
}
