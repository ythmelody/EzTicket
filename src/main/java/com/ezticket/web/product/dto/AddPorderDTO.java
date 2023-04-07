package com.ezticket.web.product.dto;


import lombok.Data;

import java.util.List;

@Data // Lombok: Gene getter/setter by @getter/@setter
public class AddPorderDTO {
    private Integer memberno;
    private Integer ptotal;
    private Integer pdiscounttotal;
    private Integer pcoupontotal;
    private Integer pchecktotal;
    private Integer pcouponno;
    private String recipient;
    private String rephone;
    private String readdress;
    private List<OrderProductDTO> orderProducts;
}
