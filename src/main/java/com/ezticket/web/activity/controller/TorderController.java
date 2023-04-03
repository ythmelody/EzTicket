package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.TorderDto;
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
    public List<TorderDto> findAll(){

        return torderService.findAll();
    }
    @GetMapping("/findById")
    public Optional<TorderDto> findById(@RequestParam Integer torderNo){

        return torderService.findById(torderNo);
    }


}
