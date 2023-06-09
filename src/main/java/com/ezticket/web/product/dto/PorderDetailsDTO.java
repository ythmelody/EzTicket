package com.ezticket.web.product.dto;

import com.ezticket.web.product.pojo.Pdetails;
import com.ezticket.web.product.pojo.Product;
import com.ezticket.web.users.pojo.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PorderDetailsDTO {
    private Integer porderno;
    private LocalDateTime porderdate;
    private Integer pchecktotal;
    private Integer pcoupontotal;
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime ppaydate;
    private List<Product> products;
    private List<Pdetails> pdetails;
    private Member member;
}
