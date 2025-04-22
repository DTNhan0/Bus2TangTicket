package com.springboot.bus2tangticket.dto.request.BusStopSchedule;

import lombok.Data;

import java.time.LocalTime;

@Data
public class BusStopScheduleRequestDTO {
    private Integer Ordertime;
    private LocalTime Time;
}
