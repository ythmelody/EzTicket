package com.ezticket.web.activity.dto;

import com.ezticket.web.activity.pojo.Aimgt;
import com.ezticket.web.activity.pojo.BlockPrice;
import com.ezticket.web.activity.pojo.Session;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import java.util.List;


@Data
public class ActivityDto {
    private Integer activityNo;
    private byte[] aSeatsImg;
    private String aName;
    private Integer aClassNo;
    private Integer hostNo;
    private String performer;
    private String aDiscrip;
    @JsonFormat(pattern = "yyyy/MM/dd kk:mm", timezone = "GMT+8")

    private Date aSDate;
    @JsonFormat(pattern = "yyyy/MM/dd kk:mm", timezone = "GMT+8")

    private Date aEDate;
    private String aTag;
    private Integer wetherSeat;
    private Integer aStatus;
    private Integer aRateTotal;
    private Integer aRateQty;
    private String aPlace;
    private String aPlaceAdress;
    private String aNote;
    private String aTicketRemind;
    private List<Session> session;
    private List<Aimgt> aimgt;
    private List <BlockPrice> blockPrice;




}
