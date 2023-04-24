package com.ezticket.web.activity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelImgDTO {
    private Integer modelno;
    private MultipartFile modelImg;
}
