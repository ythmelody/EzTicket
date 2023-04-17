package com.ezticket.web.activity.dto;

import com.ezticket.web.activity.pojo.BlockModel;
import lombok.Data;

import java.util.List;

@Data
public class PlaceToBlockModelDTO {
    private Integer modelno;
    private String modelName;
    private List<BlockModel> blockModels;
}
