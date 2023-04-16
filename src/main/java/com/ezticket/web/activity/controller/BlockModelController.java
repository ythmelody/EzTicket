package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.PlaceToBlockModelDTO;
import com.ezticket.web.activity.service.BlockModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/BlockModel")
public class BlockModelController {
    @Autowired
    private BlockModelService blockModelService;

    @GetMapping("/GetBy{modelno}")
    public PlaceToBlockModelDTO smallOne(@PathVariable("modelno") Integer modelno){
        return blockModelService.findByModelno(modelno);
    }
}
