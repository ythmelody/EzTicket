package com.ezticket.web.activity.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "torderdetailsview")
public class TorderDetailsView {
    @Id
    @Column(name = "TDETAILSNO")
    private Integer tdetailsNo;
    @Column(name = "MEMBERNO")
    private Integer memberNo;
    @Column(name = "TCHECKTOTAL")
    private Integer tcheckTotal;
    @Column(name = "TORDERNO")
    private Integer torderNo;
    @JsonFormat(pattern = "yyyy/MM/dd kk:mm:ss", timezone = "GMT+8")
    @Column(name = "TPAYDATE")
    private Timestamp tpayDate;
    @JsonFormat(pattern = "yyyy/MM/dd kk:mm:ss", timezone = "GMT+8")
    @Column(name = "TORDERDATE")
    private Timestamp torderDate;
    @Column(name = "TQTY")
    private Integer tqty;
    @Column(name = "BLOCKNAME")
    private String blockName;
    @Column(name = "BLOCKPRICE")
    private Integer blockPrice;
    @JsonFormat(pattern = "yyyy/MM/dd kk:mm:ss", timezone = "GMT+8")
    @Column(name = "SESSIONSTIME")
    private Timestamp sessionsTime;
    @JsonFormat(pattern = "yyyy/MM/dd kk:mm:ss", timezone = "GMT+8")
    @Column(name = "SESSIONSTIME")
    private Timestamp sessioneTime;
    @Column(name = "SESSIONNO")
    private Integer sessionNo;
    @Column(name = "REALX")
    private Integer realX;
    @Column(name = "REALY")
    private Integer realY;
    @Column(name = "ANAME")
    private String aname;
    @Column(name = "AIMG")
    private byte[] aimg;
    @Column(name = "WETHERSEAT")
    private Integer wetherseat;
    @Column(name = "HOSTNO")
    private Integer hostNo;

}
