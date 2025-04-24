package com.springboot.bus2tangticket.dto.response.BusRoute;

import com.springboot.bus2tangticket.dto.response.MediaFile.MediaFileResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class BusRouteListMediaFileResponseDTO {
    private BusRouteResponseDTO busRoute;
    private List<MediaFileResponseDTO> mediaList;
}
