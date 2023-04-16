package com.ezticket.web.activity.service;

import com.ezticket.web.activity.pojo.PlaceModel;
import com.ezticket.web.activity.repository.PlaceModelRepository;
import com.ezticket.web.users.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceModelService {
    @Autowired
    private PlaceModelRepository placeModelRepository;
    public List<PlaceModel> getAllPlaceModel(){
        return placeModelRepository.findAll();
    }

    public Optional<PlaceModel> findById(Integer modelno){
        return placeModelRepository.findById(modelno);
    }

//    新增模板
//    修改模板
//    public PlaceModel update(Integer modelno, PlaceModel newPlaceModel){
//        Optional<PlaceModel> oldPlaceModel = placeModelRepository.findById(modelno);
//        return placeModelRepository.saveAndFlush();
//    }

//    刪除模板
//    新增修改/刪除座位圖

//    複製模板：findAllSeatsByPlaceModelno -> insert
}
