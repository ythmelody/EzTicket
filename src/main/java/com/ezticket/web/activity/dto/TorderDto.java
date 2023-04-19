package com.ezticket.web.activity.dto;

import com.ezticket.web.users.pojo.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Data
public class TorderDto {
    private Integer torderNo;
    private Integer memberNo;
    private Integer tcheckTotal;

    @JsonFormat(pattern = "yyyy/MM/dd kk:mm:ss", timezone = "GMT+8")
    private Timestamp torderDate;
    private Integer tpaymentStatus;
    private String tprocessStatus;

    private Member member;

//    Add by Shawn on 04/17
    private Integer ttotal;





}
