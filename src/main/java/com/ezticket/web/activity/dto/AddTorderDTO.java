package com.ezticket.web.activity.dto;

import com.ezticket.web.product.dto.OrderProductDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class AddTorderDTO {

    private Integer memberno;
    private Integer ttotal;
    private Integer tcheckTotal;
    private List<OrderTicketDTO> orderTickets;
}
