package com.ezticket.web.activity.dto;

import com.ezticket.web.activity.pojo.Activity;
import lombok.Data;

import java.sql.Blob;

@Data
public class AimgtDto {
    private  Integer aimgNo;
//    private Blob aimg;
    private Activity activity;
}
