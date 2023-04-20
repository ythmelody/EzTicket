package com.ezticket.web.activity.pojo;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`SESSION`")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SESSIONNO")
    private Integer sessionNo;
    @Column(name = "ACTIVITYNO")
    private Integer activityNo;
    @Column(name = "SESSIONSTIME")
    @JsonFormat(pattern = "yyyy/MM/dd kk:mm", timezone = "GMT+8")
    private Timestamp sessionsTime;
    @Column(name = "SESSIONETIME")
    @JsonFormat(pattern = "yyyy/MM/dd kk:mm", timezone = "GMT+8")
    private Timestamp sessioneTime;
    @Column(name = "MAXSEATSQTY")
    private Integer maxSeatsQty;
    @Column(name = "MAXSTANDINGQTY")
    private Integer maxStandingQty;
    @Column(name = "SEATSQTY")
    private Integer seatsQty;
    @Column(name = "STANDINGQTY")
    private Integer standingQty;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ACTIVITYNO",insertable = false, updatable = false)
    private Activity activity;
//    @JsonBackReference
//    @ManyToOne
//    @JoinColumn(name = "ACTIVITYNO",insertable = false, updatable = false)
//    private Activity activity2;
}