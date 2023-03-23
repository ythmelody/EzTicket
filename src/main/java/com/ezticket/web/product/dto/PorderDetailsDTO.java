package webapp.porder.dto;

import lombok.Data;
import webapp.pdetails.pojo.Pdetails;
import webapp.product.pojo.Product;
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
