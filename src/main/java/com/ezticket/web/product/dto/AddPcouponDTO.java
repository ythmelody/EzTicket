package com.ezticket.web.product.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data // Lombok: Gene getter/setter by @getter/@setter
public class AddPcouponDTO {
    private Integer pcouponno;
    @NotBlank(message = "優惠券名稱不可為空值")
    private String pcouponname;
    @Min(value = 1, message = "最低消費為1~10000")
    @Max(value = 10000, message = "最低消費為0~10000")
    @NotNull(message = "最低消費不可為空值")
    private Integer preachprice;
    @Min(value = 50, message = "折扣至少50")
    @Max(value = 2000, message = "折扣不可超過2000")
    @NotNull(message = "折扣金額不可為空值")
    private Integer pdiscount;
    @NotNull(message = "開始日期不可為空值")
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime pcoupnsdate;
    @NotNull(message = "結束日期不可為空值")
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime pcoupnedate;
    @NotNull(message = "適用商品不可為空值")
    @Min(value = 10000, message = "適用商品必須伍位數")
    @Max(value = 19999, message = "適用商品必須伍位數")
    private Integer productno;
}
