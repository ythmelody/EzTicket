package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.ModelImgDTO;
import com.ezticket.web.activity.service.PlaceModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@ModelAttribute ModelImgDTO modelImgDTO, @RequestParam("file") MultipartFile file, @RequestParam("modelno") Integer modelno){
        String contentType = file.getContentType();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png") && !contentType.equals("image/gif")) {
            return ResponseEntity.badRequest().body("Invalid file type");
        }
        try {
            System.out.println("座位圖上傳成功");
            modelImgDTO.setModelImg(file);
            modelImgDTO.setModelno(modelno);
            placeModelService.updateModelImg(modelImgDTO);
            return ResponseEntity.ok("上傳成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
