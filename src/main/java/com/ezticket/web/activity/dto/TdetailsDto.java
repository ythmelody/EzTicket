package com.ezticket.web.activity.dto;

import com.ezticket.web.activity.pojo.Seats;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class TdetailsDto {
    private  Integer tdetailsNo;
    private  Integer torderNo;
    private  Integer sessionNo;
    private Integer seatNo;
    private Integer tqty;
    private Integer acommentStatus;
}
