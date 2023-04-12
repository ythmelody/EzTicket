package com.ezticket.web.activity.dto;

import com.ezticket.web.activity.pojo.Activity;
import lombok.Data;

@Data
public class AimgtDto {
    private  Integer aimgNo;
    private Integer activityNo;
    private byte[] aimg;
    private Integer aimgMain;

//    public void setAimg(byte[] aimg) {
//        this.aimg = aimg;
//    }
    private Activity activity;


}
