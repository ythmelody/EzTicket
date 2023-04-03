package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.AimgtDto;
import com.ezticket.web.activity.pojo.Aimgt;
import com.ezticket.web.activity.service.AimgtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/Aimgt")
public class AimgtController {
    @Autowired
    private AimgtService aimgtService;

    @GetMapping("/findAll")
    public List<AimgtDto> findAll(){

        return aimgtService.findAll();
    }


    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage(Integer aimgNo) {
        Optional<AimgtDto> aimgtDto = aimgtService.findById(aimgNo);
        if (aimgtDto.isPresent()) {
            byte[] image = aimgtDto.get().getAimg();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(image.length);
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}

