package com.springboot.bus2tangticket.controller.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.request.MediaFile.MediaFileDeleteRequestDTO;
import com.springboot.bus2tangticket.dto.request.MediaFile.MediaFileUploadRequestDTO;
import com.springboot.bus2tangticket.dto.response.MediaFile.MediaFileResponseDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.MediaFile;
import com.springboot.bus2tangticket.service.TuyenXeVaTramDung.MediaFileService;
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
    private MediaFileService mediaFileService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<BaseResponse<List<MediaFileResponseDTO>>> upload(
            MediaFileUploadRequestDTO requestDTO) throws Exception {

        List<MediaFile> entities = requestDTO.getFiles().stream().map(file -> {
            try {
                MediaFile mf = new MediaFile();
                mf.setFileName(file.getOriginalFilename());
                mf.setFileType(file.getContentType());
                mf.setFileData(file.getBytes());
                return mf;
            } catch (IOException e) {
                throw new RuntimeException("Lỗi đọc file", e);
            }
        }).collect(Collectors.toList());

        BaseResponse<List<MediaFile>> base = mediaFileService.createListMedia(
                requestDTO.getIdBusRoute(), requestDTO.getIdBusStop(), entities);

        List<MediaFileResponseDTO> dtoList = base.getData().stream()
                .map(mf -> modelMapper.map(mf, MediaFileResponseDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new BaseResponse<>(base.getStatus(), base.getMessage(), dtoList));
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<List<MediaFileResponseDTO>>> delete(
            @RequestBody MediaFileDeleteRequestDTO requestDTO) {

        List<MediaFile> toDelete = requestDTO.getMediaFileIds().stream().map(id -> {
            MediaFile mf = new MediaFile(); mf.setIdMediaFile(id); return mf;
        }).collect(Collectors.toList());

        BaseResponse<List<MediaFile>> base = mediaFileService.deleteListMedia(
                requestDTO.getIdBusRoute(), requestDTO.getIdBusStop(), toDelete);

        List<MediaFileResponseDTO> dtoList = base.getData().stream().map(mf -> {
            MediaFileResponseDTO dto = modelMapper.map(mf, MediaFileResponseDTO.class);
            // gán thêm hai trường thủ công
            dto.setIdBusRoute(mf.getBusRoute()  != null ? mf.getBusRoute().getIdBusRoute() : null);
            dto.setIdBusStop( mf.getBusStop()   != null ? mf.getBusStop().getIdBusStop()   : null);
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(new BaseResponse<>(base.getStatus(), base.getMessage(), dtoList));
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
}
