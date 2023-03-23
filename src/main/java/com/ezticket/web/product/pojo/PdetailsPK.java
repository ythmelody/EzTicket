package webapp.pdetails.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class PdetailsPK implements Serializable {
    @Column(name = "productno")
    private Integer productno;
    @Column(name = "porderno")
    private Integer porderno;
}