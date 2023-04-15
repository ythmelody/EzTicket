package com.ezticket.web.activity.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
public class SeatsModel {
    @Column
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
    @Column
    private Integer seatStatus;
}
