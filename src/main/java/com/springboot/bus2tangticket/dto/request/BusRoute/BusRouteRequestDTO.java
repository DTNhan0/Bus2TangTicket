package com.springboot.bus2tangticket.dto.request.BusRoute;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BusRouteRequestDTO {
    @NotNull
    private String busRouteName;

    private String overview;

    private String description;

    private String highlights;

    private String included;

    private String excluded;

    private String whatToBring;

    private String beforeYouGo;

    private Boolean isAvailable;
}
