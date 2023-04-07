package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.ACommentDto;
import com.ezticket.web.activity.dto.ActivityDto;
import com.ezticket.web.activity.pojo.Activity;
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

    // Add by Shawn on 4/3
    @GetMapping("/afterSell")
    public List<Activity> getAllActAfterSell(){
        return activityService.getAllActAfterSell();
    }

    @GetMapping("/beforeSell")
    public List<Activity> getAllActBeforeSell(){
        return activityService.getAllActBeforeSell();
    }

    @GetMapping("/selling")
    public List<Activity> getAllActSelling(){
        return activityService.getAllActSelling();
    }

    @GetMapping("/findByActNo")
    public Optional<Activity> getActById(@RequestParam Integer actNo){
        return activityService.getActById(actNo);
    }

    @GetMapping("/ByActName")
    public List<Activity> getActByBlurActName(@RequestParam String actName) {
        System.out.println(actName);
        return activityService.getActByBlurActName(actName);
    };

    @GetMapping("/findByactivityNo")
    public Optional<ActivityDto> findByactivityNo(Integer activityNo){

        return activityService.findByactivityNo(activityNo);
    }

}
