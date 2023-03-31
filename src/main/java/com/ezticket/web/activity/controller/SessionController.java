package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.SessionDto;
import com.ezticket.web.activity.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/Session")
public class SessionController {
    @Autowired
    private SessionService sessionService;

    @GetMapping("/findAll")
    public List<SessionDto> findAll(){
        return sessionService.findAll();
    }
}
