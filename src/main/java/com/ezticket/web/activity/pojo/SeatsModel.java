package com.ezticket.web.activity.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="seatsmodel")
public class SeatsModel {
    @Column(name="seatmodelno")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seatModelno;
    @Column
    private Integer blockno;
    @Column
    private Integer x;
    @Column
    private Integer y;
    @Column
    private String realx;
    @Column
    private String realy;
    @Column(name="seatstatus")
    private Integer seatStatus;
}
