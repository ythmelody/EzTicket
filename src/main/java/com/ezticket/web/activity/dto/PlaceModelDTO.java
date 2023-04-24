package com.ezticket.web.activity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlaceModelDTO {
    private Integer modelno;
    private String modelName;
    private Integer modelStatus;
}
