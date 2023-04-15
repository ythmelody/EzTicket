package com.ezticket.web.product.pojo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor
@Entity
public class Pcouponholding {
    @EmbeddedId
    private PcouponholdingPK pcouponholdingPK;
    private byte pcouponstatus;
    @ManyToOne
    @JoinColumn(name = "PCOUPONNO", insertable = false, updatable = false)
    private Pcoupon pcoupon;
}