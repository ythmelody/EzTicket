package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.PlaceModelDTO;
import com.ezticket.web.activity.pojo.PlaceModel;
import com.ezticket.web.activity.repository.BlockModelRepository;
import com.ezticket.web.activity.repository.PlaceModelRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaceModelService {
    @Autowired
    private PlaceModelRepository placeModelRepository;
    @Autowired
    private BlockModelRepository blockModelRepository;
    @Autowired
    private ModelMapper modelMapper;

    //    turn Entity into DTO
    public PlaceModelDTO EntityToDTO(PlaceModel placeModel) {
        return modelMapper.map(placeModel, PlaceModelDTO.class);
    }

    //    把查出來的資料接水管通到映射的DTO，再用一個 collect 裝起來，轉回 List
    public List<PlaceModelDTO> getAllDTO() {
        return placeModelRepository.findAll()
                .stream()
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PlaceModel> findById(Integer modelno) {
        return placeModelRepository.findById(modelno);
    }

    public PlaceModelDTO findByIdDTO(Integer modelno) {
        Optional<PlaceModel> optional = placeModelRepository.findById(modelno);
        PlaceModel placeModel = optional.get();
        return EntityToDTO(placeModel);
    }

    //    新增/修改模板
    @Transactional
    public PlaceModelDTO savePlaceModel(PlaceModelDTO placeModelDTO) {
        //      findById 有找到修改、沒找到新增
        PlaceModel placeModel = placeModelRepository.findById(placeModelDTO.getModelno()).orElse(new PlaceModel());
        BeanUtils.copyProperties(placeModelDTO, placeModel);
        PlaceModel savedPlaceModel = placeModelRepository.save(placeModel);
        BeanUtils.copyProperties(savedPlaceModel, placeModelDTO);
        return placeModelDTO;
    }

    //    修改模板(與上方法合併)

    //    刪除模板
    @Transactional
    public void deleteModel(Integer modelno) {
        placeModelRepository.deleteById(modelno);
    }

    //    查詢座位圖
    @Transactional
    public byte[] getModelImg(Integer modelno) throws Exception {
        return placeModelRepository.findById(modelno).orElseThrow().getModelImg();
    }

    //    新增修改/刪除座位圖
//    @Transactional
//    public ModelImgDTO savePlaceModelImg(Integer modelno, byte[] modelImg) {
//
//    }
//    複製模板：findAllSeatsByPlaceModelno -> insert
}
