package com.ezticket.web.activity.dto;

import com.ezticket.web.activity.pojo.AComment;
import com.ezticket.web.activity.pojo.Activity;
import com.ezticket.web.users.pojo.Member;
import lombok.Data;

import java.sql.Date;

@Data
public class ACommentDto {
    private Integer aCommentNo;
    private Date aCommentDate;
    private Member member;
    private Activity activity;
    private String aCommentCont;
    private Integer aRate;
    private Integer aLike;
    private Integer aCommentStatus;

}
