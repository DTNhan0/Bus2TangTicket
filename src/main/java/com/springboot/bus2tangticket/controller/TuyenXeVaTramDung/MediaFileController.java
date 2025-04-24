package com.springboot.bus2tangticket.controller.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.response.MediaFile.MediaFileResponseDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.MediaFile;
import com.springboot.bus2tangticket.service.TuyenXeVaTramDung.MediaFileService;
import com.springboot.bus2tangticket.service.TuyenXeVaTramDung.MediaFileServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/mediafiles")
public class MediaFileController {

    @Autowired
    private MediaFileServiceImpl mediaFileService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<MediaFileResponseDTO>> upload(
            @RequestParam(required = false) Integer idBusRoute,
            @RequestParam(required = false) Integer idBusStop,
            @RequestParam String fileName,
            @RequestPart("file") MultipartFile file
    ) throws IOException {

        MediaFile mf = new MediaFile();
        mf.setFileName(fileName);
        mf.setFileType(file.getContentType());        // Tự động lấy contentType
        mf.setFileData(file.getBytes());

        BaseResponse<MediaFile> base = mediaFileService.createMedia(idBusRoute, idBusStop, mf);
        MediaFileResponseDTO dto = null;
        if (base.getData() != null) {
            dto = modelMapper.map(base.getData(), MediaFileResponseDTO.class);
            dto.setIdBusRoute(base.getData().getBusRoute() != null
                    ? base.getData().getBusRoute().getIdBusRoute() : null);
            dto.setIdBusStop(base.getData().getBusStop() != null
                    ? base.getData().getBusStop().getIdBusStop() : null);
        }
        return ResponseEntity.ok(new BaseResponse<>(base.getStatus(), base.getMessage(), dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<MediaFileResponseDTO>> delete(@PathVariable Integer id) {
        BaseResponse<MediaFile> base = mediaFileService.deleteMedia(id);
        MediaFileResponseDTO dto = null;
        if (base.getData() != null) {
            dto = modelMapper.map(base.getData(), MediaFileResponseDTO.class);
            dto.setIdBusRoute(base.getData().getBusRoute() != null
                    ? base.getData().getBusRoute().getIdBusRoute() : null);
            dto.setIdBusStop(base.getData().getBusStop() != null
                    ? base.getData().getBusStop().getIdBusStop() : null);
        }
        return ResponseEntity.ok(new BaseResponse<>(base.getStatus(), base.getMessage(), dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> fetch(@PathVariable Integer id) {
        Optional<MediaFile> opt = mediaFileService.getMediaFileById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        MediaFile mf = opt.get();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(mf.getFileType()));
        headers.setContentDisposition(ContentDisposition.inline().filename(mf.getFileName()).build());

        return new ResponseEntity<>(mf.getFileData(), headers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<MediaFileResponseDTO>>> list(
            @RequestParam(required = false) Integer idBusRoute,
            @RequestParam(required = false) Integer idBusStop
    ) {
        BaseResponse<List<MediaFile>> base = mediaFileService.getListMedia(idBusRoute, idBusStop);
        List<MediaFileResponseDTO> dtoList = base.getData() == null
                ? null
                : base.getData().stream().map(mf -> {
            MediaFileResponseDTO dto = modelMapper.map(mf, MediaFileResponseDTO.class);
            dto.setIdBusRoute(mf.getBusRoute() != null ? mf.getBusRoute().getIdBusRoute() : null);
            dto.setIdBusStop(mf.getBusStop()  != null ? mf.getBusStop().getIdBusStop()   : null);
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(new BaseResponse<>(
                base.getStatus(), base.getMessage(), dtoList
        ));
    }
}
