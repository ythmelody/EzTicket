package com.ezticket.web.product.dto;

import com.ezticket.web.product.pojo.Pdetails;
import com.ezticket.web.product.pojo.Product;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
public class PorderDetailsDTO {
    private Integer porderno;
    private Timestamp porderdate;
    private Integer ptotal;
    private Timestamp ppaydate;
    private List<Product> products;
    private List<Pdetails> pdetails;
}
