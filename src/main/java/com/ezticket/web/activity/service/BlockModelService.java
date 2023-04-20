package com.ezticket.web.activity.service;

import com.ezticket.web.activity.dto.BlockModelDTO;
import com.ezticket.web.activity.dto.PlaceModelDTO;
import com.ezticket.web.activity.dto.PlaceToBlockModelDTO;
import com.ezticket.web.activity.pojo.BlockModel;
import com.ezticket.web.activity.pojo.PlaceModel;
import com.ezticket.web.activity.repository.BlockModelRepository;
import com.ezticket.web.activity.repository.PlaceModelRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlockModelService {
    @Autowired
    private PlaceModelRepository placeModelRepository;
    @Autowired
    private BlockModelRepository blockModelRepository;

    //    由 modelno 查出 modelname 和 BlockModel
//    PlaceToBlockModelDTO 的屬性有 modelno, modelname, <List>BlockModel
    public PlaceToBlockModelDTO findByModelno(Integer modelno) {
        PlaceModel placeModel = placeModelRepository.findById(modelno).orElseThrow();
        PlaceToBlockModelDTO ptb = new PlaceToBlockModelDTO();
        BeanUtils.copyProperties(placeModel, ptb);
        return ptb;
    }

    public BlockModelDTO findByIdDTO(Integer blockno) {
        Optional<BlockModel> optional = blockModelRepository.findById(blockno);
        BlockModel blockModel = optional.get();
        BlockModelDTO blockModelDTO= new BlockModelDTO();
        BeanUtils.copyProperties(blockModel, blockModelDTO);
        return blockModelDTO;
    }


        //    新增/修改區域
    @Transactional
    public BlockModelDTO saveBlockModel(BlockModelDTO blockModelDTO) {
        BlockModel blockModel;
        if (blockModelDTO.getBlockno() != null) {
            //      findById 有找到修改、沒找到新增
            blockModel = blockModelRepository.findById(blockModelDTO.getBlockno()).orElse(new BlockModel());
        } else {
            blockModel = new BlockModel();
        }
        BeanUtils.copyProperties(blockModelDTO, blockModel);
        BlockModel savedBlockModel = blockModelRepository.save(blockModel);
        BeanUtils.copyProperties(savedBlockModel, blockModelDTO);
        return blockModelDTO;
    }

//    修改區域(使用上述方法)

    //    刪除區域
    @Transactional
    public void deleteBlock(Integer blockno) {
        blockModelRepository.deleteById(blockno);
    }

//    模糊搜尋名稱
}
