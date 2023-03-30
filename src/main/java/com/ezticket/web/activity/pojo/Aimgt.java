package com.ezticket.web.activity.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AIMGT")
public class Aimgt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AIMGNO")
    private  Integer aimgNo;
   @Column(name="AIMG")
    private Blob aimg;
//    @ManyToOne
//    @JoinColumn(name = "AIMGNO")
//    private Activity activity;




}
