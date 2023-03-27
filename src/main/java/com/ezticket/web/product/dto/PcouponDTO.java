package com.ezticket.web.product.dto;


import com.ezticket.web.product.pojo.Pfitcoupon;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data // Lombok: Gene getter/setter by @getter/@setter
public class PcouponDTO {

    private Integer pcouponno;
    private String pcouponname;
    private Integer preachprice;
    private Integer pdiscount;
    private LocalDateTime pcoupnsdate;
    private LocalDateTime pcoupnedate;
    private byte pcouponstatus;
    private List<Pfitcoupon> pfitcoupons;
}
