package webapp.pfitcoupon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webapp.pfitcoupon.dto.PfitcouponDTO;
import webapp.pfitcoupon.service.PfitcouponService;

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
