package com.springboot.bus2tangticket.dto.response.BusStop;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.bus2tangticket.dto.response.MediaFile.MediaFileResponseDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BusStopNoBusRouteResponseDTO {
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

    private List<MediaFileResponseDTO> mediaBusStopList;
}
