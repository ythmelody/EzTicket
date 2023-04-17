package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.TorderDto;
import com.ezticket.web.activity.pojo.Torder;
import com.ezticket.web.activity.service.TorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/Torder")
public class TorderController {
    @Autowired
    private TorderService torderService;

    @GetMapping ("/findAll")
    public List<TorderDto> findByOrderByTorderNoDesc(){

        return torderService.findByOrderByTorderNoDesc();
    }
    @GetMapping("/findById")
    public Optional<TorderDto> findById( Integer torderNo){

        return torderService.findById(torderNo);
    }


}
