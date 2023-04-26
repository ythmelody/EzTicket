package com.ezticket.web.activity.pojo;

import com.ezticket.web.users.pojo.Host;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
    @JsonFormat(pattern = "yyyy/MM/dd kk:mm", timezone = "GMT+8")

    @Column(name = "ASDATE")
    private Date aSDate;
    @JsonFormat(pattern = "yyyy/MM/dd kk:mm", timezone = "GMT+8")

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
    @Column(name = "APLACE")
    private String aPlace;
    @Column(name = "APLACEADDRESS")
    private String aPlaceAdress;
    @Column(name = "ANOTE")
    private String aNote;
    @Column(name = "ATICKETREMIND")
    private String aTicketRemind;
    @JsonManagedReference
    @OneToMany(mappedBy = "activity")
    private List<Session> session;
    @JsonManagedReference
    @OneToMany(mappedBy = "activity", fetch = FetchType.LAZY)
    private List<Aimgt> aimgt;
    @JsonManagedReference
    @OneToMany(mappedBy = "activity", fetch = FetchType.LAZY)
    private List <BlockPrice> blockPrice;



}
