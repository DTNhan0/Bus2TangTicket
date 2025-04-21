package com.springboot.bus2tangticket.dto.response.BusStop;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.bus2tangticket.dto.response.BusRoute.BusRouteResponseDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BusStopResponseDTO {
    private Integer idBusStop;

    private Integer idParent;

    private String busStopName;

    private String introduction;

    private String address;

    private Integer stopOrder = -1;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss",
            timezone = "Asia/Ho_Chi_Minh" // Thêm múi giờ [[1]][[2]]
    )
    private LocalDateTime updateAt;

    private Boolean isAvailable;

    private BusRouteResponseDTO busRoute;
}
