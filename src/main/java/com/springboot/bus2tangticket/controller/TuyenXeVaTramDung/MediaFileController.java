package com.springboot.bus2tangticket.controller.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.MediaFile;
import com.springboot.bus2tangticket.service.TuyenXeVaTramDung.MediaFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/media")
@RequiredArgsConstructor
@CrossOrigin
public class MediaFileController {

    @Autowired
    private final MediaFileService mediaFileService;

    @PostMapping("/upload")
    public BaseResponse<List<MediaFile>> uploadImages(
            @RequestParam(required = false, defaultValue = "0") int idBusRoute,
            @RequestParam(required = false, defaultValue = "0") int idBusStop,
            @RequestParam("files") List<MultipartFile> files) {

        List<MediaFile> mediaFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                MediaFile mf = new MediaFile();
                mf.setFileName(file.getOriginalFilename());
                mf.setFileType(file.getContentType());
                mf.setFileData(file.getBytes());
                mediaFiles.add(mf);
            } catch (IOException e) {
                return new BaseResponse<>(ResponseStatus.SUCCESS, "Lỗi khi xử lý file: " + file.getOriginalFilename(), null);
            }
        }

        return mediaFileService.createListMedia(idBusRoute, idBusStop, mediaFiles);
    }

    @GetMapping("/{idMediaFile}")
    public ResponseEntity<byte[]> getImageById(@PathVariable("idMediaFile") Integer idMediaFile) {
        return mediaFileService.getMediaFileById(idMediaFile)
                .map(mediaFile -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.parseMediaType(mediaFile.getFileType()));
                    headers.setContentLength(mediaFile.getFileData().length);
                    headers.setContentDispositionFormData("attachment", mediaFile.getFileName());

                    return new ResponseEntity<>(mediaFile.getFileData(), headers, HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete")
    public BaseResponse<List<MediaFile>> deleteMedia(
            @RequestParam(required = false, defaultValue = "0") int idBusRoute,
            @RequestParam(required = false, defaultValue = "0") int idBusStop,
            @RequestBody List<MediaFile> mediaFileList) {
        return mediaFileService.deleteListMedia(idBusRoute, idBusStop, mediaFileList);
    }
}
