package com.ezticket.web.product.controller;

import com.ezticket.web.product.dto.AddPorderDTO;
import com.ezticket.web.product.dto.PorderDTO;
import com.ezticket.web.product.dto.PorderDetailsDTO;
import com.ezticket.web.product.dto.PorderStatusDTO;
import com.ezticket.web.product.service.PorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/porder")
public class PorderController {

    @Autowired private PorderService porderService;

    @GetMapping("/list")
    public List<PorderDTO> getAllPorderlist(){
        return porderService.getAllPorder();
    }

    @GetMapping("/getporderbyid")
    public PorderDTO getDeliveryByID(@RequestParam Integer id){
        return porderService.getPorderByID(id);
    }
    @GetMapping("/GetPorderDetailsByID")
    public PorderDetailsDTO getDetailByID(@RequestParam Integer id){
        return porderService.getPorderDetailsByID(id);
    }
    @GetMapping("/getordersbyid")
    public List<PorderDTO> getPordersByID(@RequestParam Integer id){
        return porderService.getPordersByID(id);
    }
    @PostMapping("/updatestatusbyid")
    @ResponseBody
    public boolean updateStatusByID(@RequestBody PorderStatusDTO ps){
        porderService.updateByID(ps.getPorderno(), ps.getPprocessstatus());
        return true;
    }
    @PostMapping("/add")
    @ResponseBody
    public boolean addPorder(@RequestBody AddPorderDTO addPorderDTO){
        porderService.AddPorder(addPorderDTO);
        return true;
    }
}
