package com.springboot.bus2tangticket.dto.request.BusStop;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BusStopRequestDTO {

    @NotNull
    private String busStopName;

    @NotNull
    private String introduction;

    @NotNull
    private String address;

    @NotNull
    @Min(-1)
    private Integer stopOrder;

    @NotNull
    private Boolean isAvailable;
}
