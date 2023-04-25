package com.ezticket.web.product.pojo;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @JsonBackReference
    private Pcoupon pcoupon;
}