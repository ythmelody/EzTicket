package com.ezticket.web.activity.dto;

import com.ezticket.web.activity.pojo.Aimgt;
import com.ezticket.web.activity.pojo.Session;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.sql.Date;
import java.util.List;


@Data
public class ActivityDto {
    private Integer activityNo;
    private String aName;
    private Date aSDate;
    private Date aEDate;
    private Integer aStatus;
    private String aPlace;
    private String aDiscrip;
    private String aNote;
    private String aTicketRemind;

    private List<Session> session;
    private List<Aimgt> aimgt;





}
