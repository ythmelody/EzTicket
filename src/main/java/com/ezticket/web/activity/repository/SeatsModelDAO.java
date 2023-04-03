package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.SeatsModelVO;

import java.util.List;

public interface SeatsModelDAO {
    void insert(SeatsModelVO seatsModelVO);

    void update(SeatsModelVO seatsModelVO);

    void delete(Integer seatModelno);

    SeatsModelVO findByPK(Integer seatModelno);

    List<SeatsModelVO> getAll();
}
