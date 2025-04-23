package com.springboot.bus2tangticket.dto.response.RouteDepartureDate;

import lombok.Data;


import java.time.LocalDate;

@Data
public class RouteDepartureDateResponseDTO {

    private Integer idRouteDepartureDate;

    private LocalDate date;

    private Boolean status;
}
