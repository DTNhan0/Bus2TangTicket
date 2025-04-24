package com.springboot.bus2tangticket.dto.request.MediaFile;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MediaFileUploadRequestDTO {
    private Integer idBusRoute;
    private Integer idBusStop;
    private String fileName;
    private MultipartFile files;
}
