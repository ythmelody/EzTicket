package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.ActivityDto;
import com.ezticket.web.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/Activity")

public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @GetMapping("/findAll")
    public List<ActivityDto> findByOrderByActivityNoDesc(){

        return activityService.findByOrderByActivityNoDesc();
    }
    @GetMapping("/findByaName")
    public Optional<ActivityDto> findByAname(String aName){
        return activityService.findByaName(aName);
    }
    @GetMapping("/findByactivityNo")
    public Optional<ActivityDto> findByactivityNo(Integer activityNo){

        return activityService.findByactivityNo(activityNo);
    }

}
