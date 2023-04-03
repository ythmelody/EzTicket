package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.AclassDto;
import com.ezticket.web.activity.service.AclassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/Aclass")
public class AclassController {
    @Autowired
    private AclassService aclassService;
    @GetMapping("/findAll")
    public List<AclassDto> findAll(){
        return aclassService.findAll();
    }
}
