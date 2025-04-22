package com.springboot.bus2tangticket.dto.request.BusRoute;

import lombok.Data;

import java.util.List;

@Data
public class BusRouteListBusStopRequestDTO {
    List<Integer> idBusStopList;
}
