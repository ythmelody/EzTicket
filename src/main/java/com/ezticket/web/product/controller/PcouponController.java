package com.ezticket.web.product.controller;

import com.ezticket.web.product.dto.AddPcouponDTO;
import com.ezticket.web.product.dto.PcouponDTO;
import com.ezticket.web.product.dto.PcouponStatusDTO;
import com.ezticket.web.product.service.PcouponService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/getbystring")
    public List<PcouponDTO> getPcouponsByString(@RequestParam String pcouponname){
        return pcouponService.getPcouponsByName(pcouponname);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPcoupon (@Valid @RequestBody AddPcouponDTO couponBody, BindingResult bindingResult) {
        return pcouponService.addPcouponError(couponBody, bindingResult);
    }

    @PostMapping("/edit")
    @ResponseBody
    public ResponseEntity<?> editPcoupon(@Valid @RequestBody AddPcouponDTO couponBody, BindingResult bindingResult){
        return pcouponService.editPcouponError(couponBody, bindingResult);
    }
    @PostMapping("/updateStatus")
    @ResponseBody
    public boolean updateByID(@RequestBody PcouponStatusDTO ps) {
        return pcouponService.updateByID(ps.getPcouponno(),ps.getPcouponstatus());
    }
}
