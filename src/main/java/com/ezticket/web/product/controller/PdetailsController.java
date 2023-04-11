package com.ezticket.web.product.controller;

import com.ezticket.web.product.dto.PdetailsDTO;
import com.ezticket.web.product.dto.PdetailsStatusDTO;
import com.ezticket.web.product.pojo.PdetailsPK;
import com.ezticket.web.product.service.PdetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pdetails")
public class PdetailsController {

    @Autowired private PdetailsService pdetailsService;
    @Autowired private PdetailsPK pdetailsPK;

    @GetMapping("/list")
    public List<PdetailsDTO> getAllPdetailslist(){return pdetailsService.getAllPdetails();}

    @GetMapping("/byPorderno") //取得單筆訂單明細的單個品項
    public PdetailsStatusDTO getByPorderno(@RequestParam Integer porderno, @RequestParam Integer productno){
        pdetailsPK.setPorderno(porderno);
        pdetailsPK.setProductno(productno);
        return pdetailsService.getPdetailsStatusByID(pdetailsPK);
    }

}
