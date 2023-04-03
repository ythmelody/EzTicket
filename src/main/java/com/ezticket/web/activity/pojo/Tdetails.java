package com.ezticket.web.activity.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tdetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TDETAILSNO")
    private  Integer tdetailsNo;
    @Column(name = "TORDERNO")
    private  Integer torderNo;
    @Column(name = "SESSIONNO")
    private  Integer sessionNo;
    @Column(name = "SEATNO")
    private Integer seatNo;
    @Column(name = "TQTY")
    private Integer tqty;
    @Column(name = "ACOMMENTSTATUS")
    private Integer acommentStatus;



}
