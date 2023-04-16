package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.service.PlaceModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/ModelImg")
public class ModelImgController {
    @Autowired
    PlaceModelService placeModelService;

    @GetMapping (value = "/{modelno}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getModelImg(@PathVariable("modelno") Integer modelno) throws IOException {
        byte[] img;
        try {
            img = placeModelService.getModelImg(modelno);
        } catch (Exception e) {
            System.out.println(".......................photo not found");
            Path noimg = Paths.get("images/event-imgs/qmark.jpg");
            img = Files.readAllBytes(noimg);
        }
        return img;
    }
}
