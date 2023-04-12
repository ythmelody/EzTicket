package com.ezticket.web.users.controller;

import com.ezticket.web.users.pojo.Host;
import com.ezticket.web.users.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/back-users-host")
public class HostController {
    @Autowired
    private HostService hostService;
    @GetMapping("/gh")
    public List<Host> getAllHost(){ return  hostService.getAllHost(); }

}
