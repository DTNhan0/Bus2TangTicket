package com.springboot.bus2tangticket.dto.response.BusStopSchedule;

import com.springboot.bus2tangticket.dto.response.BusStop.BusStopResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class BusStopShceduleListBusStopResponseDTO {
    private BusStopResponseDTO busStop;
    private List<BusStopShceduleResponseDTO> shceduleList;
}
