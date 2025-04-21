package com.springboot.bus2tangticket.dto.response.TicketPrice;

import com.springboot.bus2tangticket.dto.response.BusRoute.BusRouteResponseDTO;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.TicketPrice;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketPriceResponseDTO {

    private Integer idTicketPrice;

    private BigDecimal parentPrice;

    private BigDecimal childPrice;

    private String ticketType;

    private Boolean status;

    private BusRouteResponseDTO busRoute;
}
