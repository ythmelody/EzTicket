package com.ezticket.web.activity.repository;

import com.ezticket.web.activity.pojo.PlaceModelVO;

import java.util.List;

public interface PlaceModelDAO {

    void insert(PlaceModelVO placeModelVO);

    void update(PlaceModelVO placeModelVO);

    void delete(Integer modelno);

    PlaceModelVO findByPK(Integer modelno);

    List<PlaceModelVO> getAll();

}
