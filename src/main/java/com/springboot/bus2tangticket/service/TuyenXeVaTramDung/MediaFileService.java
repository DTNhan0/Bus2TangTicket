package com.springboot.bus2tangticket.service.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.MediaFile;

import java.util.List;
import java.util.Optional;

public interface MediaFileService {
    BaseResponse<List<MediaFile>> createListMedia(int idBusRoute, int idBusStop, List<MediaFile> mediaFileList);
    BaseResponse<List<MediaFile>> deleteListMedia(int idBusRoute, int idBusStop, List<MediaFile> mediaFileList);
    Optional<MediaFile> getMediaFileById(Integer id);
}
