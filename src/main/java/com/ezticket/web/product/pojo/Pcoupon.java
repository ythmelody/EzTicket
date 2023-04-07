package com.ezticket.web.product.pojo;


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
@Table(name = "Pcoupon")
public class Pcoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PCOUPONNO")
    private Integer pcouponno;
    @Column(name = "PCOUPONNAME", length = 20)
    private String pcouponname;
    @Column(name = "PREACHPRICE")
    private Integer preachprice;
    @Column(name = "PDISCOUNT")
    private Integer pdiscount;
    @Column(name = "PCOUPNSDATE")
    private LocalDateTime pcoupnsdate;
    @Column(name = "PCOUPNEDATE")
    private LocalDateTime pcoupnedate;
    @Column(name = "PCOUPONSTATUS")
    private byte pcouponstatus;
    @OneToMany(mappedBy = "pfitcouponNo.pcouponno", cascade = CascadeType.ALL)
    private List<Pfitcoupon> pfitcoupons;
    @OneToMany(mappedBy = "pcouponholdingPK.pcouponno", cascade = CascadeType.ALL)
    private List<Pcouponholding> pcouponholdings;
}
