package com.ezticket.web.activity.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COLLECT")
public class Collect {
    @Column(name = "COLLECTNO")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer collectno;
    @Column(name = "MEMBERNO")
    private Integer memberno;
    @Column(name = "TDETAILSNO")
    private Integer tdetailsno;
    @Column(name = "TSTATUS")
    private Integer tstatus;
    @Column(name = "QRCODE")
    private String qrcode;
}
