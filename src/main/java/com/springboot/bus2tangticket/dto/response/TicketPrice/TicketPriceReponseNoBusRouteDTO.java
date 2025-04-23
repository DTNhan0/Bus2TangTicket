package com.springboot.bus2tangticket.dto.response.TicketPrice;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketPriceReponseNoBusRouteDTO {
    private Integer idTicketPrice;

    private BigDecimal parentPrice;

    private BigDecimal childPrice;

    private String ticketType;

    private Boolean status;
}
