package com.ezticket.web.activity.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Column(name = "ACTIVITYNO")
    private Integer activityNo;
   @Column(name="AIMG")
    private byte[] aimg;
    @Column(name="AIMGMAIN")
   private Integer aimgMain;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ACTIVITYNO",
            insertable = false, updatable = false)
    private Activity activity;





}
