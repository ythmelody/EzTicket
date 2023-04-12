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
import java.io.InputStream;
import java.util.Arrays;
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

    @GetMapping("/findAllByActivityNo/{activityNo}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer activityNo) {
        List<AimgtDto> aimgtDtos = aimgtService.findAllByActivityNo(activityNo);
        if (!aimgtDtos.isEmpty()) {
            AimgtDto aimgtDto = aimgtDtos.get(0);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(aimgtDto.getAimg().length);
            return new ResponseEntity<>(aimgtDto.getAimg(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    }





