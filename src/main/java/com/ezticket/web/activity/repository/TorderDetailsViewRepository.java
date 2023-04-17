package com.ezticket.web.activity.repository;


import com.ezticket.web.activity.pojo.TorderDetailsView;
import com.ezticket.web.activity.pojo.TorderView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TorderDetailsViewRepository extends JpaRepository<TorderDetailsView,Integer> {
    List<TorderDetailsView> findAllBytorderNo(Integer torderNo);
}
