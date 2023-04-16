package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.PlaceToBlockModelDTO;
import com.ezticket.web.activity.pojo.PlaceModel;
import com.ezticket.web.activity.repository.BlockModelRepository;
import com.ezticket.web.activity.repository.PlaceModelRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockModelService {
    @Autowired
    private PlaceModelRepository placeModelRepository;
    @Autowired
    private BlockModelRepository blockModelRepository;
//    由 modelno 查出 modelname 和 BlockModel
//    PlaceToBlockModelDTO 的屬性有 modelno, modelname, <List>BlockModel
    public PlaceToBlockModelDTO findByModelno(Integer modelno){
        PlaceModel placeModel = placeModelRepository.findById(modelno).orElseThrow();
        PlaceToBlockModelDTO ptb = new PlaceToBlockModelDTO();
        BeanUtils.copyProperties(placeModel, ptb);
        return ptb;
    }
//    新增區域
//    修改區域
//    刪除區域

//    模糊搜尋名稱、區域編號
}
