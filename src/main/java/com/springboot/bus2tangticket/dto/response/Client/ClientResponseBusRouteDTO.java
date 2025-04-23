package com.springboot.bus2tangticket.dto.response.Client;

import com.springboot.bus2tangticket.dto.response.BusRoute.BusRouteResponseDTO;
import com.springboot.bus2tangticket.dto.response.BusStop.BusStopNoBusRouteResponseDTO;
import com.springboot.bus2tangticket.dto.response.MediaFile.MediaFileResponseDTO;
import com.springboot.bus2tangticket.dto.response.TicketPrice.TicketPriceReponseNoBusRouteDTO;
import lombok.Data;

import java.util.List;

@Data
public class ClientResponseBusRouteDTO {
    private BusRouteResponseDTO busRoute;
    private List<TicketPriceReponseNoBusRouteDTO> ticketPriceList;
    private List<BusStopNoBusRouteResponseDTO> busStopList;
    private List<MediaFileResponseDTO> mediaBusRouteList;

}
