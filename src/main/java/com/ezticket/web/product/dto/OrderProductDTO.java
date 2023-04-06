package com.ezticket.web.product.dto;

import lombok.Data;

@Data
public class OrderProductDTO {
    private Integer productno;
    private Integer quantity;
    private Integer pprice;
}
