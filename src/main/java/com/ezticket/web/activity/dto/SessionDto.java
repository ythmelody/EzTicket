package com.ezticket.web.activity.dto;

import com.ezticket.web.activity.pojo.Activity;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import java.util.Calendar;
@Data
public class SessionDto {
    private Integer sessionNo;
    @JsonFormat(pattern = "yyyy/MM/dd kk:mm", timezone = "GMT+8")
    private Calendar sessionsTime;
    @JsonFormat(pattern = "yyyy/MM/dd kk:mm", timezone = "GMT+8")

    private Calendar sessioneTime;

    private Integer maxSeatsQty;

    private Integer maxStandingQty;

    private Integer seatsQty;

    private Integer standingQty;

    private Integer activityNo;

    private Activity activity;
}
