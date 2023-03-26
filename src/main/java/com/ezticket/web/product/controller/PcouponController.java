package com.ezticket.web.product.controller;

import com.ezticket.web.product.dto.PcouponDTO;
import com.ezticket.web.product.service.PcouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pcoupon")
public class PcouponController {

    @Autowired private PcouponService pcouponService;

    @GetMapping("/list")
    public List<PcouponDTO> getAllPcoupon(){
        return pcouponService.getAllPcoupon();
    }

    @GetMapping
    public PcouponDTO getPcouponByID(@RequestParam Integer id){
        return pcouponService.getPcouponByID(id);
    }
}
