package com.ezticket.web.product.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "請填入收件人")
    private String recipient;
    @NotNull(message = "請填入收件人手機")
    private String rephone;
    @NotNull(message = "請填入地址")
    @Size(min=15, message="地址長度不足")
    private String readdress;
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime porderdate;
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime ppaydate;
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime pshipdate;
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime parrivedate;
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime pclosedate;
    private Integer ppaymentstatus;
    private Integer pprocessstatus;
}
