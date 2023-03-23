package com.ezticket.web.product.controller;

import com.ezticket.web.product.dto.PdetailsDTO;
import com.ezticket.web.product.service.PdetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/html")
public class PdetailsController {

    @Autowired private PdetailsService pdetailsService;

    @GetMapping("/pdetailslist")
    public List<PdetailsDTO> getAllPdetailslist(){return pdetailsService.getAllPdetails();}

}
