package com.springboot.bus2tangticket.dto.response.BusStopSchedule;

import lombok.Data;

import java.time.LocalTime;

@Data
public class BusStopShceduleResponseDTO {
    private Integer idDepartureTime;

    private Integer orderTime;

    private LocalTime time;
}
