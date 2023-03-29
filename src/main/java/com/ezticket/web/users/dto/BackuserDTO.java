package com.ezticket.web.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BackuserDTO {
    private Integer bano;
    private String baaccount;
    private String baname;
    private String baemail;
    private Integer baroleno;
    private String bacell;
    private Integer bastatus;
}
