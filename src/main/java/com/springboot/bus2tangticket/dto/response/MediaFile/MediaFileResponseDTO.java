package com.springboot.bus2tangticket.dto.response.MediaFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaFileResponseDTO {
    private Integer idMediaFile;
    private Integer idBusRoute;
    private Integer idBusStop;
    private String fileName;
    private String fileType;
}


