package com.ezticket.web.activity.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SEATS")
public class Seats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEATNO")
    private Integer seatNo;
    @Column(name = "SESSIONNO")
    private Integer sessionNo;
    @Column(name = "BLOCKNO")
    private Integer blockNo;
    @Column(name = "BLOCKNAME")
    private String blockName;
    @Column(name = "X")
    private Integer x;
    @Column(name = "Y")
    private Integer y;
    @Column(name = "REALX")
    private String realX;
    @Column(name = "REALY")
    private String realY;
    @Column(name = "SEATSTATUS")
    private Integer seatStatus;

//    @ManyToOne
//    @JoinColumn(name = "sessionNo", insertable = false, updatable = false)
//    private Session session;

    @ManyToOne
    @JoinColumn(name = "BLOCKNO", insertable = false, updatable = false)
    private BlockPrice blockPrice;
}
