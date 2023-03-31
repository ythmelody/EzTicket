package com.ezticket.web.activity.dto;

import com.ezticket.web.activity.pojo.Activity;
import lombok.Data;
import java.util.Calendar;
@Data
public class SessionDto {
    private Integer sessionNo;

    private Calendar sessionsTime;

    private Calendar sessioneTime;

    private Integer maxSeatsQty;

    private Integer maxStandingQty;

    private Integer seatsQty;

    private Integer standingQty;

    private Activity activityNo;
}
