package com.springboot.bus2tangticket.dto.response.RouteDepartureDate;

import com.springboot.bus2tangticket.dto.response.BusRoute.BusRouteResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class RouteDepartureDateListResponseDTO {
    private BusRouteResponseDTO busRoute;
    private List<RouteDepartureDateResponseDTO> routerDepartureDateResponseList;
}
