package com.ezticket.web.product.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // Lombok: Gene getter/setter by @getter/@setter
@AllArgsConstructor // Lombok: Auto gene args constructor
@NoArgsConstructor // Lombok: read above
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productno;
    private Integer pclassno;
    private String pname;
    private Integer hostno;
    private String pdiscrip;
    private Integer pprice;
    private Integer pspecialprice;
    private Integer pqty;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime psdate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pedate;
    private String ptag;
    private byte pstatus;
    private Integer pratetotal;
    private Integer prateqty;

//    @ManyToMany(mappedBy = "products")
//    private List<Porder> porders;
}
