package com.springboot.bus2tangticket.dto.request.MediaFile;

import lombok.Data;

import java.util.List;

@Data
public class MediaFileDeleteRequestDTO {
    private Integer idBusRoute;
    private Integer idBusStop;
    private List<Integer> mediaFileIds;
}
