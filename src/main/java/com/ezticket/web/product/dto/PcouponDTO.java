package com.ezticket.web.product.dto;


import com.ezticket.web.product.pojo.Pfitcoupon;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data // Lombok: Gene getter/setter by @getter/@setter
public class PcouponDTO {

    private Integer pcouponno;
    private String pcouponname;
    private Integer preachprice;
    private Integer pdiscount;
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime pcoupnsdate;
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime pcoupnedate;
    private byte pcouponstatus;
    private List<Pfitcoupon> pfitcoupons;
}
