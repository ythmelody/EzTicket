package com.ezticket.web.product.dto;


import lombok.Data;

import java.sql.Timestamp;

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
    private Timestamp porderdate;
    private Timestamp ppaydate;
    private Timestamp pshipdate;
    private Timestamp parrivedate;
    private Timestamp pclosedate;
    private Integer ppaymentstatus;
    private Integer pprocessstatus;
}
