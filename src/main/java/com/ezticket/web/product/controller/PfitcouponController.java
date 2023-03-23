package com.ezticket.web.product.controller;

import com.ezticket.web.product.dto.PfitcouponDTO;
import com.ezticket.web.product.service.PfitcouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/html")
public class PfitcouponController {

    @Autowired private PfitcouponService pfitcouponService;

    @GetMapping("/pfitcouponlist")
    public List<PfitcouponDTO> getAllPfitcouponlist(){
        return pfitcouponService.getAllPfitcoupon();
    }

}
