package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.ActivityDto;
import com.ezticket.web.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/Activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @GetMapping("/findAll")
    public List<ActivityDto> findAll(){
        return activityService.findAll();
    }
}
