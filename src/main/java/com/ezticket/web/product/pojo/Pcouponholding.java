package com.ezticket.web.product.pojo;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;


@Data // Lombok: Gene getter/setter by @getter/@setter
@Entity
public class Pcouponholding {
    @EmbeddedId
    private PcouponholdingPK pcouponholdingPK;
    private byte pcouponstatus;

}
