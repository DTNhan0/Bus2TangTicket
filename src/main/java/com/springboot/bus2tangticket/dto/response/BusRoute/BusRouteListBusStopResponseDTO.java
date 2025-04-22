package com.springboot.bus2tangticket.dto.response.BusRoute;

import com.springboot.bus2tangticket.dto.response.BusStop.BusStopNoBusRouteResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class BusRouteListBusStopResponseDTO {
    private BusRouteResponseDTO busRoute;
    private List<BusStopNoBusRouteResponseDTO> busStopList;
}
