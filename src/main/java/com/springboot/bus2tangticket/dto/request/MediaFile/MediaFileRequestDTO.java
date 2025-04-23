package com.springboot.bus2tangticket.dto.request.MediaFile;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MediaFileRequestDTO {
    private Integer idBusRoute;
    private Integer idBusStop;
    private MultipartFile file;
}


