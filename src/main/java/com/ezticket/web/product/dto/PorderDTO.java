package com.ezticket.web.product.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PorderDTO {
    private Integer porderno;
    private Integer memberno;
    private Integer ptotal;
    private Integer pdiscounttotal;
    private Integer pcoupontotal;
    private Integer pchecktotal;
    private Integer pcouponno;
    private String recipient;
    private String rephone;
    private String readdress;
    private LocalDateTime porderdate;
    private LocalDateTime ppaydate;
    private LocalDateTime pshipdate;
    private LocalDateTime parrivedate;
    private LocalDateTime pclosedate;
    private Integer ppaymentstatus;
    private Integer pprocessstatus;
}
