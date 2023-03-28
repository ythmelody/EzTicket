package com.ezticket.web.activity.dto;

import com.ezticket.web.activity.pojo.AComment;
import com.ezticket.web.users.pojo.Member;
import lombok.Data;

import java.sql.Date;

@Data
public class AReportDto {
    private Integer aReportNo;
    private Date aReportDate;
    private Member member;
    private String aWhy;
    private AComment aComment;
    private Integer aReportStatus;
}
