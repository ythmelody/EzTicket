package webapp.product.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import webapp.porder.pojo.Porder;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

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
    private Timestamp psdate;
    private Timestamp pedate;
    private String ptag;
    private byte pstatus;
    private Integer pratetotal;
    private Integer prateqty;

//    @ManyToMany(mappedBy = "products")
//    private List<Porder> porders;
}
