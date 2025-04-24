package com.springboot.bus2tangticket.service.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.MediaFile;

import java.util.List;
import java.util.Optional;

public interface MediaFileService {
    BaseResponse<MediaFile> createMedia(Integer idBusRoute, Integer idBusStop, MediaFile mediaFile);
    BaseResponse<MediaFile> deleteMedia(Integer idMediaFile);
    BaseResponse<List<MediaFile>> getListMedia(Integer idBusRoute, Integer idBusStop);
    Optional<MediaFile> getMediaFileById(Integer id);
}

