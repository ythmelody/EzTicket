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
@Table(name = "torderview")
public class TorderView {
    @Id
    @Column(name = "TORDERNO")
    private Integer torderNo;
    @Column(name = "MEMBERNO")
    private Integer memberNo;
    @JsonFormat(pattern = "yyyy/MM/dd kk:mm:ss", timezone = "GMT+8")
    @Column(name = "TORDERDATE")
    private Timestamp torderDate;
    @Column(name = "TCHECKTOTAL")
    private Integer tcheckTotal;
    @Column(name = "TPAYMENTSTATUS")
    private Integer tpaymentStatus;
    @Column(name = "ANAME")
    private String aname;
    @Column(name = "AIMG")
    private byte[] aimg;



}
