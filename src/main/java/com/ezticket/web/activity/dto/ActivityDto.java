package com.ezticket.web.activity.dto;

import com.ezticket.web.activity.pojo.Session;
import lombok.Data;
import java.sql.Date;
@Data
public class ActivityDto {
    private String aName;
    private Date aSDate;
    private Date aEDate;
    private Session session;




}
