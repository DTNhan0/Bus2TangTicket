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
    public BaseResponse<List<MediaFile>> createListMedia(
            Integer idBusRoute, Integer idBusStop, List<MediaFile> mediaFileList) {

        resetAutoIncrement();
        // Phải truyền đúng một trong idBusRoute hoặc idBusStop
        if ((idBusRoute == null && idBusStop == null) || (idBusRoute != null && idBusStop != null)) {
            return new BaseResponse<>(
                    ResponseStatus.FAILED,
                    "Phải truyền đúng một trong idBusRoute hoặc idBusStop",
                    null
            );
        }

        BusRoute busRoute = null;
        BusStop busStop = null;

        if (idBusRoute != null) {
            Optional<BusRoute> routeOpt = busRouteRepo.findById(idBusRoute);
            if (routeOpt.isEmpty()) {
                return new BaseResponse<>(
                        ResponseStatus.FAILED,
                        "Không tìm thấy busRoute với id: " + idBusRoute,
                        null
                );
            }
            busRoute = routeOpt.get();
        } else {
            // idBusStop != null
            busStop = busStopService.getBusStop(idBusStop).getData();
            if (busStop == null) {
                return new BaseResponse<>(
                        ResponseStatus.FAILED,
                        "Không tìm thấy busStop với id: " + idBusStop,
                        null
                );
            }
            busRoute = busStop.getBusRoute();
        }

        List<MediaFile> saved = new ArrayList<>();
        for (MediaFile mf : mediaFileList) {
            mf.setBusRoute(busRoute);
            mf.setBusStop(busStop);
            saved.add(mediaFileRepo.save(mf));
        }

        return new BaseResponse<>(
                ResponseStatus.SUCCESS,
                "Upload media files thành công!",
                saved
        );
    }

    @Override
    @Transactional
    public BaseResponse<List<MediaFile>> deleteListMedia(
            Integer idBusRoute, Integer idBusStop, List<MediaFile> mediaFileList) {

        // Phải truyền đúng một trong idBusRoute hoặc idBusStop
        if ((idBusRoute == null && idBusStop == null) || (idBusRoute != null && idBusStop != null)) {
            return new BaseResponse<>(
                    ResponseStatus.FAILED,
                    "Phải truyền đúng một trong idBusRoute hoặc idBusStop",
                    null
            );
        }

        List<MediaFile> toDelete = new ArrayList<>();
        for (MediaFile mf : mediaFileList) {
            Optional<MediaFile> opt = mediaFileRepo.findById(mf.getIdMediaFile());
            if (opt.isEmpty()) {
                return new BaseResponse<>(
                        ResponseStatus.FAILED,
                        "Không tìm thấy mediaFile id: " + mf.getIdMediaFile(),
                        null
                );
            }
            toDelete.add(opt.get());
        }

        mediaFileRepo.deleteAll(toDelete);
        return new BaseResponse<>(
                ResponseStatus.SUCCESS,
                "Xóa media files thành công!",
                toDelete
        );
    }

    @Override
    public Optional<MediaFile> getMediaFileById(Integer id) {
        return mediaFileRepo.findById(id);
    }
}
