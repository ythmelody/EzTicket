package com.ezticket.web.activity.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACTIVITY")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTIVITYNO")
    private Integer activityNo;
    @Column(name = "ASEATSIMG")
    private byte[] aSeatsImg;
    @Column(name = "ANAME")
    private String aName;
    @Column(name = "ACLASSNO")
    private Integer aClassNo;
    @Column(name = "HOSTNO")
    private Integer hostNo;
    @Column(name = "PERFORMER")
    private String performer;
    @Column(name = "ADISCRIP")
    private String aDiscrip;
    @Column(name = "ASDATE")
    private Date aSDate;
    @Column(name = "AEDATE")
    private Date aEDate;
    @Column(name = "ATAG")
    private String aTag;
    @Column(name = "WETHERSEAT")
    private Integer wetherSeat;
    @Column(name = "ASTATUS")
    private Integer aStatus;
    @Column(name = "ARATETOTAL")
    private Integer aRateTotal;
    @Column(name = "ARATEQTY")
    private Integer aRateQty;
}
