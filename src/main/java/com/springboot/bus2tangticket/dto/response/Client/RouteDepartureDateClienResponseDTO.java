package com.springboot.bus2tangticket.dto.response.Client;

import com.springboot.bus2tangticket.dto.response.RouteDepartureDate.RouteDepartureDateResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class RouteDepartureDateClienResponseDTO {
    private List<RouteDepartureDateResponseDTO> routerDepartureDateResponseList;
}
