package com.ezticket.web.product.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

//@Data
//@AllArgsConstructor // Lombok: Auto gene args constructor
//@NoArgsConstructor
@Embeddable
public class PcouponholdingPK implements Serializable {
    @Column(name = "PCOUPONNO")
    private Integer pcouponno;
    @Column(name = "MEMBERNO")
    private Integer memberno;

    public PcouponholdingPK() {
    }

    public PcouponholdingPK(Integer pcouponno, Integer memberno) {
        this.pcouponno = pcouponno;
        this.memberno = memberno;
    }

    public Integer getPcouponno() {
        return pcouponno;
    }

    public void setPcouponno(Integer pcouponno) {
        this.pcouponno = pcouponno;
    }

    public Integer getMemberno() {
        return memberno;
    }

    public void setMemberno(Integer memberno) {
        this.memberno = memberno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PcouponholdingPK other = (PcouponholdingPK) o;
        return Objects.equals(pcouponno, other.pcouponno)
                && Objects.equals(memberno, other.memberno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pcouponno, memberno);
    }
}