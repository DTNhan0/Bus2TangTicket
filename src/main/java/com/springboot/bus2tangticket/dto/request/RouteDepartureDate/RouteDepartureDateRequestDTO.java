package com.springboot.bus2tangticket.dto.request.RouteDepartureDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RouteDepartureDateRequestDTO {
    @NotNull
    private LocalDate date;

    @NotNull
    private Boolean status;
}
