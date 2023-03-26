package com.ezticket.web.product.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class PcouponholdingPK implements Serializable {
    @Column(name = "PCOUPONNO")
    private Integer pcouponno;
    @Column(name = "MEMBERNO")
    private Integer memberno;
}