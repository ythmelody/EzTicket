package com.ezticket.web.product.dto;

import com.ezticket.web.product.pojo.PdetailsPK;
import lombok.Data;


@Data
public class PdetailsStatusDTO {
    private PdetailsPK pdetailsNo;
    private Integer pcommentstatus;
}
