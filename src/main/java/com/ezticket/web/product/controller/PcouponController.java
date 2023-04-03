package com.ezticket.web.product.controller;

import com.ezticket.web.product.dto.AddPcouponDTO;
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
    @GetMapping("/getbyno")
    public List<PcouponDTO> getPcouponsByID(@RequestParam Integer id){
        return pcouponService.getPcouponsByID(id);
    }
    @PostMapping("/add")
    @ResponseBody
    public boolean postAddPcoupon(@RequestBody AddPcouponDTO couponBody){
        return pcouponService.addPcoupon(couponBody);
    }
}
