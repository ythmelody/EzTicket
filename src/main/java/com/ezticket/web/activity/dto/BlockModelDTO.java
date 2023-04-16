package com.ezticket.web.activity.dto;

import com.ezticket.web.activity.pojo.SeatsModel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class BlockModelDTO {
    private Integer blockno;
    private String blockName;
    private Integer modelno;
    private Integer blockType;
}
