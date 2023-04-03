package com.ezticket.web.activity.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class BlockPriceDto {
    private Integer blockNo;
    private String blockName;
    private Integer activityNo;
    private Integer blockPrice;
}
