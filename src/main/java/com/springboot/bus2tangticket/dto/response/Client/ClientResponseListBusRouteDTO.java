package com.springboot.bus2tangticket.dto.response.Client;


import com.springboot.bus2tangticket.dto.response.MediaFile.MediaFileResponseDTO;
import com.springboot.bus2tangticket.dto.response.TicketPrice.TicketPriceReponseNoBusRouteDTO;

import lombok.Data;

import java.util.List;

@Data
public class ClientResponseListBusRouteDTO {
    private Integer idBusRoute;
    private Integer idParent;
    private String busRouteName;
    private String overview;
    private String description;
    private List<TicketPriceReponseNoBusRouteDTO> ticketPriceList;
    private List<MediaFileResponseDTO> mediaBusRouteList;
}
