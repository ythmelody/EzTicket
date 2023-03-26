package com.ezticket.web.product.pojo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // Lombok: Gene getter/setter by @getter/@setter
@Entity
public class Pcouponholding {
    @EmbeddedId
    private PcouponholdingPK pcouponholdingPK;
    private byte pcouponstatus;

}
