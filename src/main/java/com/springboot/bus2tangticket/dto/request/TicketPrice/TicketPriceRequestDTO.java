package com.springboot.bus2tangticket.dto.request.TicketPrice;

import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.TicketPrice;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketPriceRequestDTO {

    private BigDecimal parentPrice;

    private BigDecimal childPrice;

    @Enumerated(EnumType.STRING)
    private String ticketType;

    private Boolean status;

}
