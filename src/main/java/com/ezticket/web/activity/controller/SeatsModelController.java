package com.ezticket.web.activity.controller;

import com.ezticket.web.activity.dto.PlaceModelDTO;
import com.ezticket.web.activity.dto.SeatsModelDTO;
import com.ezticket.web.activity.pojo.Seats;
import com.ezticket.web.activity.pojo.SeatsModel;
import com.ezticket.web.activity.service.SeatsModelService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/SeatsModel")
public class SeatsModelController {

    @Autowired
    private SeatsModelService seatsModelService;

    @GetMapping("/ByBlockno/{blockno}")
    public List<SeatsModel> getByBlockno(@PathVariable("blockno") Integer blockno){
        return seatsModelService.findByBlockno(blockno);
    }

    @GetMapping("/ExistOrNot/{blockno}")
    public boolean existOrNot(@PathVariable("blockno") Integer blockno){
        return seatsModelService.existOrNot(blockno);
    }

    @PostMapping("/insert")
    public boolean insert(@RequestBody String jsonData){
        Gson gson = new Gson();
        SeatsModel seatsModel = gson.fromJson(jsonData, SeatsModel.class);
        seatsModelService.insert(seatsModel);
        return true;
    }

    @PostMapping("/save")
    public boolean saveSeatsModel(@RequestBody SeatsModelDTO seatsModelDTO){
        return seatsModelService.saveSeatsModel(seatsModelDTO);
    }

    @GetMapping("/clear/{blockno}")
    public boolean clearSeatsModel(@PathVariable("blockno") Integer blockno){
        return seatsModelService.clearSeatsModel(blockno);
    }

}
