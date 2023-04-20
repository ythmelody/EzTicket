package com.ezticket.web.activity.dto;

import lombok.Data;

@Data
public class OrderTicketDTO {
    private Integer sessionNo;
    private Integer seatNo;
    private Integer tQty;
}
