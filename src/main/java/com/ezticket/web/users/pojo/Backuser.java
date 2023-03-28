package com.ezticket.web.users.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BACKUSER")
public class Backuser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BANO")
    private Integer bano;
    @Column(name = "BAACCOUNT")
    private String baaccount;
    @Column(name = "BAPASSWORD")
    private String bapassword;
    @Column(name = "BANAME")
    private String baname;
    @Column(name = "BAEMAIL")
    private String baemail;
    @Column(name = "BACELL")
    private String bacell;
    @Column(name = "BAROLENO")
    private Integer baroleno;
    @Column(name = "BASTATUS")
    private Integer bastatus;
}
