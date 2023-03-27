package com.ezticket.web.product.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
@Entity
public class Porder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer porderno;
    private Integer memberno;
    private Integer ptotal;
    private Integer pdiscounttotal;
    private Integer pcoupontotal;
    private Integer pchecktotal;
    private Integer pcouponno;
    private String recipient;
    private String rephone;
    private String readdress;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime porderdate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ppaydate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pshipdate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime parrivedate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pclosedate;
    private Integer ppaymentstatus;
    private Integer pprocessstatus;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(referencedColumnName = "porderno", name = "porderno"),
            name = "pdetails",
            inverseJoinColumns = @JoinColumn(name = "productno", referencedColumnName = "productno")
    )
    private List<Product> products;
    @OneToMany(mappedBy = "pdetailsNo.porderno")
    private List<Pdetails> pdetails;

}
