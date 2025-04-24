package com.springboot.bus2tangticket.dto.response.Statistic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RouteStatsDTO {
    private Integer month;
    private Integer routeId;
    private String routeName;
    private Long passengerCount;
}
