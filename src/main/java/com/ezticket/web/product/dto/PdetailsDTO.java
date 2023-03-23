package webapp.pdetails.dto;

import webapp.pdetails.pojo.PdetailsPK;
import lombok.Data;

@Data
public class PdetailsDTO {
    private PdetailsPK pdetailsNo;
    private Integer porderqty;
    private Integer pprice;
    private Integer pcommentstatus;

}