package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.PlaceModelDTO;
import com.ezticket.web.activity.pojo.PlaceModel;
import com.ezticket.web.activity.repository.PlaceModelRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaceModelService {
    @Autowired
    private PlaceModelRepository placeModelRepository;
    @Autowired
    private ModelMapper modelMapper;
    private final ResourceLoader resourceLoader;

    public PlaceModelService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

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

//    取得啟用中模板
    public List<PlaceModelDTO> getActive() {
        return placeModelRepository.findAll()
                .stream()
                .filter(p -> p.getModelStatus() == 1)
                .map(this::EntityToDTO)
                .collect(Collectors.toList());
    }

//    取得停用中模板
    public List<PlaceModelDTO> getDisabled() {
        return placeModelRepository.findAll()
                .stream()
                .filter(p -> p.getModelStatus() == 0)
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
        PlaceModel placeModel = new PlaceModel();
//        if (placeModelDTO.getModelno()!=null) {
//            //      findById 有找到修改、沒找到新增
//            placeModel = placeModelRepository.findById(placeModelDTO.getModelno()).orElse(new PlaceModel());
//        } else {
//            placeModel = new PlaceModel();
//        }
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
//        Optional<PlaceModel> opt = placeModelRepository.findById(modelno);
//        System.out.println(opt);
//        byte[] test = placeModelRepository.findById(modelno).orElseThrow().getModelImg();
//        System.out.println(test);

        byte[] img = null;
        try {
            img = placeModelRepository.findById(modelno).orElseThrow().getModelImg();
            System.out.println(img + ".......................DB資料有進來 service");
            if (img != null) {
                System.out.println("............................有圖片");
                return img;
            }
        } catch (Exception e) {
            System.out.println("..................data base img error");
        }
        Resource resource = resourceLoader.getResource("classpath:static/images/event-imgs/qmark.jpg");
        try (InputStream inputStream = resource.getInputStream()) {
            img = inputStream.readAllBytes();
        }
        System.out.println("................................無圖片，顯示預設圖");
        return img;
    }

//    新增修改/刪除座位圖

//    複製模板：findAllSeatsByPlaceModelno -> insert
}
