package com.ezticket.web.activity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlockModelDTO {
    private Integer blockno;
    private String blockName;
    private Integer modelno;
    private Integer blockType;
}
