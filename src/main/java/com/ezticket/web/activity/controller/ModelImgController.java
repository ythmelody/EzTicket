package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.service.PlaceModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ModelImg")
public class ModelImgController {
    @Autowired
    PlaceModelService placeModelService;

    @GetMapping(value = "/{modelno}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getModelImg(@PathVariable("modelno") Integer modelno){
        byte[] img = null;
        try{
            img = placeModelService.getModelImg(modelno);
        } catch (Exception e) {
            System.out.println("...................default img error");
        }
        return img;
    }
}
