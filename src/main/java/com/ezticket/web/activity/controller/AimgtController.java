package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.AimgtDto;
import com.ezticket.web.activity.service.AimgtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

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
}
