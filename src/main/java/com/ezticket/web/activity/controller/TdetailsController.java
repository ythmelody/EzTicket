package com.ezticket.web.activity.controller;


import com.ezticket.web.activity.dto.TdetailsDto;
import com.ezticket.web.activity.pojo.Tdetails;
import com.ezticket.web.activity.service.TdetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/Tdetails")
public class TdetailsController {

    @Autowired
    private TdetailsService adetailsService;

    @GetMapping("/findAll")
    public List<TdetailsDto> findAll(){
        return  adetailsService.findAll();
    }

}
