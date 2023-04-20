package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.BlockModelDTO;
import com.ezticket.web.activity.dto.PlaceModelDTO;
import com.ezticket.web.activity.dto.PlaceToBlockModelDTO;
import com.ezticket.web.activity.service.BlockModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/BlockModel")
public class BlockModelController {
    @Autowired
    private BlockModelService blockModelService;

//    由模板號碼查出其區域及座位（模板僅含編號及名稱）
    @GetMapping("/GetBy{modelno}")
    public PlaceToBlockModelDTO smallOne(@PathVariable("modelno") Integer modelno){
        return blockModelService.findByModelno(modelno);
    }

    @GetMapping("/Get/{blockno}")
    public BlockModelDTO getOne(@PathVariable("blockno") Integer blockno) {
        return blockModelService.findByIdDTO(blockno);
    }

    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<?> insertModel(@RequestBody BlockModelDTO blockModelDTO){
        BlockModelDTO blockModelDTOr = blockModelService.saveBlockModel(blockModelDTO);
        return ResponseEntity.ok(blockModelDTOr);
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<?> editModel(@RequestBody BlockModelDTO blockModelDTO){
        BlockModelDTO blockModelDTOr = blockModelService.saveBlockModel(blockModelDTO);
        return ResponseEntity.ok(blockModelDTOr);
    }

    @PostMapping("/delete/{blockno}")
    public void deleteBlock(@PathVariable("blockno") Integer blockno){
        blockModelService.deleteBlock(blockno);
        System.out.println("BlockModel " + blockno + " is deleted.");
    }

}
