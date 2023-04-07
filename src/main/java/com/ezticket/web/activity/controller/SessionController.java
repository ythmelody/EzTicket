package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.AimgtDto;
import com.ezticket.web.activity.dto.SessionDto;
import com.ezticket.web.activity.dto.TorderDto;
import com.ezticket.web.activity.pojo.Session;
import com.ezticket.web.activity.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Base64;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/Session")
public class SessionController {
    @Autowired
    private SessionService sessionService;
    private static final Base64.Encoder ENCODER = Base64.getEncoder();

    @GetMapping("/findAll")
    public List<SessionDto> findAll() {

        return sessionService.findAll();
    }

    @GetMapping("/findByactivityNo")
    public List<SessionDto> findByactivityNo(@RequestParam Integer activityNo){

        return sessionService.findByactivityNo(activityNo);
    }

    //    Add by Shawn on 4/3
    @GetMapping("/ActSessions")
    public List<SessionDto> getAllSessionByActNo(@RequestParam Integer actNo) {
        return sessionService.getAllSessionByActNo(actNo);
    }
}
