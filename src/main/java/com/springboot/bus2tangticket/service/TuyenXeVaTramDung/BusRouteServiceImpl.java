package com.springboot.bus2tangticket.service.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusStop;
import com.springboot.bus2tangticket.repository.BusRouteRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusRouteServiceImpl implements BusRouteService {

    @Autowired
    private BusRouteRepo busRouteRepo;

    @Autowired
    private BusStopServiceImpl busStopService;

    @PersistenceContext
    private EntityManager em;

    private void resetAutoIncrement() {
        em.createNativeQuery("ALTER TABLE busroute AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    @Autowired
    public BusRouteServiceImpl(BusRouteRepo busRouteRepo) {
        this.busRouteRepo = busRouteRepo;
    }

    @Override
    @Transactional
    public BaseResponse<BusRoute> createBusRoute(BusRoute busRoute) {
        resetAutoIncrement();
        for (BusRoute br : busRouteRepo.findAll()) {
            if (busRoute.getBusRouteName().equals(br.getBusRouteName())) {
                return new BaseResponse<>(ResponseStatus.FAILED,
                        "Tên busRouteName đã tồn tại trên hệ thống!", null);
            }
        }
        BusRoute saved = busRouteRepo.save(busRoute);
        return new BaseResponse<>(ResponseStatus.SUCCESS,
                "Tạo busroute thành công!", saved);
    }

    @Override
    public BaseResponse<List<BusRoute>> getAllBusRoute() {
        return new BaseResponse<>(ResponseStatus.SUCCESS,
                "Lấy danh sách busroute thành công!", busRouteRepo.findAll());
    }

    @Override
    public BaseResponse<BusRoute> getBusRoute(int idBusRoute) {
        BusRoute br = busRouteRepo.findById(idBusRoute).orElse(null);
        if (br == null) {
            return new BaseResponse<>(ResponseStatus.FAILED,
                    "Không tìm thấy busroute có id: " + idBusRoute, null);
        }
        return new BaseResponse<>(ResponseStatus.SUCCESS,
                "Đã tìm thấy busroute có id: " + idBusRoute, br);
    }

    @Override
    @Transactional
    public BaseResponse<BusRoute> updateBusRoute(int idBusRoute, BusRoute busRoute) {
        BaseResponse<BusRoute> base = getBusRoute(idBusRoute);
        if (base.getData() == null) {
            return new BaseResponse<>(base.getStatus(), base.getMessage(), null);
        }
        BusRoute existing = base.getData();
        // ... (giữ nguyên logic đổi tên / ghi đè fields như trước) ...
        existing.setBusRouteName(busRoute.getBusRouteName());
        existing.setOverview(busRoute.getOverview());
        existing.setDescription(busRoute.getDescription());
        existing.setHighlights(busRoute.getHighlights());
        existing.setIncluded(busRoute.getIncluded());
        existing.setExcluded(busRoute.getExcluded());
        existing.setWhatToBring(busRoute.getWhatToBring());
        existing.setBeforeYouGo(busRoute.getBeforeYouGo());
        existing.setIsAvailable(busRoute.getIsAvailable());
        BusRoute updated = busRouteRepo.save(existing);
        return new BaseResponse<>(ResponseStatus.SUCCESS,
                "Đã cập nhật busRoute!", updated);
    }

    @Override
    @Transactional
    public BaseResponse<BusRoute> deleteBusRoute(int idBusRoute) {
        BusRoute br = getBusRoute(idBusRoute).getData();
        if (br == null) {
            return new BaseResponse<>(ResponseStatus.FAILED,
                    "Không tìm thấy busroute với id: " + idBusRoute, null);
        }
        try {
            busRouteRepo.deleteById(idBusRoute);
        } catch (DataIntegrityViolationException e) {
            return new BaseResponse<>(ResponseStatus.FAILED,
                    "Lỗi ràng buộc dữ liệu!", br);
        }
        return new BaseResponse<>(ResponseStatus.SUCCESS,
                "Xóa thành công", br);
    }

    @Override
    @Transactional
    public BaseResponse<BusRoute> addListBusStopToBusRoute(
            int idBusRoute, List<Integer> idBusStopList) {

        BusRoute route = getBusRoute(idBusRoute).getData();
        if (route == null) {
            return new BaseResponse<>(ResponseStatus.FAILED,
                    "Không tìm thấy busRoute có id: " + idBusRoute, null);
        }

        List<BusStop> toAdd = new ArrayList<>();
        for (Integer id : idBusStopList) {
            BusStop bs = busStopService.getBusStop(id).getData();
            if (bs == null) {
                return new BaseResponse<>(ResponseStatus.FAILED,
                        "Không tìm thấy busStop có id: " + id, null);
            }
            if (bs.getBusRoute() != null) {
                return new BaseResponse<>(ResponseStatus.FAILED,
                        "BusStop id " + id + " đã nằm trong busRoute khác!", null);
            }
            toAdd.add(bs);
        }

        // Cập nhật
        for (BusStop bs : toAdd) {
            bs.setBusRoute(route);
            busStopService.updateBusStop(bs.getIdBusStop(), bs);
        }

        route = getBusRoute(idBusRoute).getData();
        return new BaseResponse<>(ResponseStatus.SUCCESS,
                "Đã thêm busstop thành công vào busroute id: " + idBusRoute, route);
    }

    @Transactional
    public BaseResponse<BusRoute> removeListBusStopFromBusRoute(
            int idBusRoute, List<Integer> idBusStopList) {

        BusRoute route = getBusRoute(idBusRoute).getData();
        if (route == null) {
            return new BaseResponse<>(ResponseStatus.FAILED,
                    "Không tìm thấy busRoute có id: " + idBusRoute, null);
        }

        List<BusStop> toRemove = new ArrayList<>();
        for (Integer id : idBusStopList) {
            BusStop bs = busStopService.getBusStop(id).getData();
            if (bs == null) {
                return new BaseResponse<>(ResponseStatus.FAILED,
                        "Không tìm thấy busStop có id: " + id, null);
            }
            if (bs.getBusRoute() == null ||
                    !bs.getBusRoute().getIdBusRoute().equals(idBusRoute)) {
                return new BaseResponse<>(ResponseStatus.FAILED,
                        "BusStop id " + id + " không thuộc busRoute id: " + idBusRoute, null);
            }
            toRemove.add(bs);
        }

        // Xóa ràng buộc
        for (BusStop bs : toRemove) {
            bs.setBusRoute(null);
            busStopService.updateBusStop(bs.getIdBusStop(), bs);
        }

        route = getBusRoute(idBusRoute).getData();
        return new BaseResponse<>(ResponseStatus.SUCCESS,
                "Đã xóa các busStop khỏi busRoute id: " + idBusRoute, route);
    }
}
