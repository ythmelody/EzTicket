package com.ezticket.web.activity.dto;

import com.ezticket.web.activity.pojo.BlockModel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class PlaceModelDTO {
    private Integer modelno;
    private String modelName;
    private Integer modelStatus;
}
