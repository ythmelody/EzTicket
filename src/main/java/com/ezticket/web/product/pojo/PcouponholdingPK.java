package com.ezticket.web.product.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor
@Embeddable
public class PcouponholdingPK implements Serializable {
    @Column(name = "PCOUPONNO")
    private Integer pcouponno;
    @Column(name = "MEMBERNO")
    private Integer memberno;
}