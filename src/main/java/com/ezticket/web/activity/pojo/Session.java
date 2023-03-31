package com.ezticket.web.activity.pojo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
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
    @Column(name = "SESSIONSTIME")
    private Calendar sessionsTime;
    @Column(name = "SESSIONETIME")
    private Calendar sessioneTime;
    @Column(name = "MAXSEATSQTY")
    private Integer maxSeatsQty;
    @Column(name = "MAXSTANDINGQTY")
    private Integer maxStandingQty;
    @Column(name = "SEATSQTY")
    private Integer seatsQty;
    @Column(name = "STANDINGQTY")
    private Integer standingQty;
//    @ManyToOne
//    @JoinColumn(name = "ACTIVITYNO")
//    private Activity activityNo;
}