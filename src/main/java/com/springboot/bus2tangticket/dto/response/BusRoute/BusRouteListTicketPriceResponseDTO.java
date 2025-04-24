package com.springboot.bus2tangticket.dto.response.BusRoute;

import com.springboot.bus2tangticket.dto.response.TicketPrice.TicketPriceReponseNoBusRouteDTO;
import lombok.Data;

import java.util.List;

@Data
public class BusRouteListTicketPriceResponseDTO {
    private BusRouteResponseDTO busRoute;
    private List<TicketPriceReponseNoBusRouteDTO> ticketPriceList;
}
