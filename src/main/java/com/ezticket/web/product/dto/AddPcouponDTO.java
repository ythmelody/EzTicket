package com.ezticket.web.product.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data // Lombok: Gene getter/setter by @getter/@setter
public class AddPcouponDTO {
    private Integer pcouponno;
    private String pcouponname;
    private Integer preachprice;
    private Integer pdiscount;
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime pcoupnsdate;
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime pcoupnedate;
    private Integer productno;
}
