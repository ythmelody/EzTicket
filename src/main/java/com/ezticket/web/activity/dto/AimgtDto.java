package com.ezticket.web.activity.dto;

import com.ezticket.web.activity.pojo.Activity;
import lombok.Data;

import java.sql.Blob;
import java.util.Base64;

@Data
public class AimgtDto {
    private  Integer aimgNo;
    private Integer activityNo;
    private byte[] aimg;
    private Integer aimgMain;

    private Activity activity;


}
