package com.springboot.bus2tangticket.service.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusStop;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.MediaFile;
import com.springboot.bus2tangticket.repository.BusRouteRepo;
import com.springboot.bus2tangticket.repository.BusStopRepo;
import com.springboot.bus2tangticket.repository.MediaFileRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MediaFileServiceImpl implements MediaFileService {

    @Autowired
    private final MediaFileRepo mediaFileRepo;

    @Autowired
    private final BusRouteRepo busRouteRepo;

    @Autowired
    private final BusStopRepo busStopRepo;

    @Override
    @Transactional
    public BaseResponse<List<MediaFile>> createListMedia(int idBusRoute, int idBusStop, List<MediaFile> mediaFileList) {
        BusRoute route = idBusRoute != 0 ? busRouteRepo.findById(idBusRoute).orElse(null) : null;
        BusStop stop = idBusStop != 0 ? busStopRepo.findById(idBusStop).orElse(null) : null;

        for (MediaFile mf : mediaFileList) {
            mf.setBusRoute(route);
            mf.setBusStop(stop);
        }

        List<MediaFile> saved = mediaFileRepo.saveAll(mediaFileList);
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Upload thành công", saved);
    }

    @Override
    @Transactional
    public BaseResponse<List<MediaFile>> deleteListMedia(int idBusRoute, int idBusStop, List<MediaFile> mediaFileList) {
        mediaFileRepo.deleteAll(mediaFileList);
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã xóa danh sách media", mediaFileList);
    }

    @Override
    public Optional<MediaFile> getMediaFileById(Integer id) {
        return mediaFileRepo.findById(id);
    }
}
