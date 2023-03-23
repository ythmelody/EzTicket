package com.ezticket.web.product.dto;

import com.ezticket.web.product.pojo.PdetailsPK;
import lombok.Data;

@Data
public class PdetailsDTO {
    private PdetailsPK pdetailsNo;
    private Integer porderqty;
    private Integer pprice;
    private Integer pcommentstatus;

}