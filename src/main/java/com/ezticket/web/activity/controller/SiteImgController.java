package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.SiteImgDTO;
import com.ezticket.web.activity.service.PlaceModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/SiteImg")
public class SiteImgController {
    @Autowired
    PlaceModelService placeModelService;

//    @GetMapping(value = "/{modelno}", produces = MediaType.IMAGE_JPEG_VALUE)
//    public byte[] getSiteImg(@PathVariable("modelno") Integer modelno){
//        byte[] img = null;
//        try{
//            img = placeModelService.getSiteImg(modelno);
//        } catch (Exception e) {
//            System.out.println("...................default img error");
//        }
//        return img;
//    }

//    @PostMapping("/upload")
//    public ResponseEntity<String> upload(@ModelAttribute SiteImgDTO siteImgDTO, @RequestParam("file") MultipartFile file, @RequestParam("modelno") Integer modelno){
//        try {
//            System.out.println("場地外觀圖上傳成功");
//            siteImgDTO.setSiteImg(file);
//            siteImgDTO.setModelno(modelno);
//            placeModelService.updateSiteImg(siteImgDTO);
//            return ResponseEntity.ok("上傳成功");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
