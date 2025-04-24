package com.springboot.bus2tangticket.service.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusStop;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.MediaFile;
import com.springboot.bus2tangticket.repository.BusRouteRepo;
import com.springboot.bus2tangticket.repository.MediaFileRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MediaFileServiceImpl implements MediaFileService {

    @Autowired
    private MediaFileRepo mediaFileRepo;

    @Autowired
    private BusRouteRepo busRouteRepo;

    @Autowired
    private BusStopServiceImpl busStopService;

    @PersistenceContext
    private EntityManager em;

    private void resetAutoIncrement() {
        // Thiết lập AUTO_INCREMENT về 1 (tức next = max(id)+1)
        em.createNativeQuery("ALTER TABLE mediafile AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    @Override
    @Transactional
    public BaseResponse<MediaFile> createMedia(Integer idBusRoute, Integer idBusStop, MediaFile mediaFile) {
        resetAutoIncrement();
        if ((idBusRoute == null && idBusStop == null) || (idBusRoute != null && idBusStop != null)) {
            return new BaseResponse<>(ResponseStatus.FAILED,
                    "Phải truyền đúng một trong idBusRoute hoặc idBusStop", null);
        }

        BusRoute route = null;
        BusStop stop = null;
        if (idBusRoute != null) {
            route = busRouteRepo.findById(idBusRoute).orElse(null);
            if (route == null)
                return new BaseResponse<>(ResponseStatus.FAILED,
                        "Không tìm thấy busRoute id: " + idBusRoute, null);
        } else {
            stop = busStopService.getBusStop(idBusStop).getData();
            if (stop == null)
                return new BaseResponse<>(ResponseStatus.FAILED,
                        "Không tìm thấy busStop id: " + idBusStop, null);
            route = stop.getBusRoute();
        }

        mediaFile.setBusRoute(route);
        mediaFile.setBusStop(stop);
        // fileType lấy từ field đã set ở controller
        MediaFile saved = mediaFileRepo.save(mediaFile);
        return new BaseResponse<>(ResponseStatus.SUCCESS,
                "Upload file thành công!", saved);
    }
    @Override
    @Transactional

    public BaseResponse<MediaFile> deleteMedia(Integer idMediaFile) {
        Optional<MediaFile> opt = mediaFileRepo.findById(idMediaFile);
        if (opt.isEmpty()) {
            return new BaseResponse<>(ResponseStatus.FAILED,
                    "Không tìm thấy mediaFile id: " + idMediaFile, null);
        }
        MediaFile mf = opt.get();
        mediaFileRepo.delete(mf);
        return new BaseResponse<>(ResponseStatus.SUCCESS,
                "Xóa file thành công!", mf);
    }

    @Override
    public Optional<MediaFile> getMediaFileById(Integer id) {
        return mediaFileRepo.findById(id);
    }

    @Override
    public BaseResponse<List<MediaFile>> getListMedia(Integer idBusRoute, Integer idBusStop) {
        // phải truyền đúng 1 trong 2
        if ((idBusRoute == null && idBusStop == null) ||
                (idBusRoute != null && idBusStop != null))
        {
            return new BaseResponse<>(
                    ResponseStatus.FAILED,
                    "Phải truyền đúng một trong idBusRoute hoặc idBusStop",
                    null
            );
        }

        List<MediaFile> list;
        if (idBusRoute != null) {
            // kiểm tra tồn tại route
            if (!busRouteRepo.existsById(idBusRoute)) {
                return new BaseResponse<>(
                        ResponseStatus.FAILED,
                        "Không tìm thấy busRoute với id: " + idBusRoute,
                        null
                );
            }
            list = mediaFileRepo.findAllByBusRoute_IdBusRoute(idBusRoute);
        } else {
            // kiểm tra tồn tại stop
            BusStop stop = busStopService.getBusStop(idBusStop).getData();
            if (stop == null) {
                return new BaseResponse<>(
                        ResponseStatus.FAILED,
                        "Không tìm thấy busStop với id: " + idBusStop,
                        null
                );
            }
            list = mediaFileRepo.findAllByBusStop_IdBusStop(idBusStop);
        }

        return new BaseResponse<>(ResponseStatus.SUCCESS,
                "Lấy danh sách media files thành công",
                list);
    }
}
