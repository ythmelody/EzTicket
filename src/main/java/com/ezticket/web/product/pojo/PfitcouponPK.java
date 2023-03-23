package com.ezticket.web.product.pojo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
@Embeddable
public class PfitcouponPK implements Serializable {
    private Integer pcouponno;
    private Integer productno;

    // getters and setters
}
