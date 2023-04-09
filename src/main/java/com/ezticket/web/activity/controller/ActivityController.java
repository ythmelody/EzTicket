package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.ACommentDto;
import com.ezticket.web.activity.dto.ActivityDto;
import com.ezticket.web.activity.dto.AimgtDto;
import com.ezticket.web.activity.pojo.Activity;
import com.ezticket.web.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/findAllByActivityNo/{activityNo}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer activityNo) {
        List<ActivityDto> activityDtos = activityService.findAllByActivityNo(activityNo);
        if (!activityDtos.isEmpty()) {
            ActivityDto activityDto = activityDtos.get(0);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(activityDto.getASeatsImg().length);
            return new ResponseEntity<>(activityDto.getASeatsImg(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
