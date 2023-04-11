package com.ezticket.web.product.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Embeddable
@Component
public class PdetailsPK implements Serializable {
    @Column(name = "PRODUCTNO")
    private Integer productno;
    @Column(name = "PORDERNO")
    private Integer porderno;
}