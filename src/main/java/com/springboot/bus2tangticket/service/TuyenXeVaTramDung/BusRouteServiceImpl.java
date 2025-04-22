package com.springboot.bus2tangticket.service.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusStop;
import com.springboot.bus2tangticket.repository.BusRouteRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusRouteServiceImpl implements BusRouteService{

    @Autowired
    BusRouteRepo busRouteRepo;

    @Autowired
    BusStopServiceImpl busStopService;

    @PersistenceContext
    private EntityManager em;

    private void resetAutoIncrement() {
        // Thiết lập AUTO_INCREMENT về 1 (tức next = max(id)+1)
        em.createNativeQuery("ALTER TABLE busroute AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    @Autowired
    public BusRouteServiceImpl(BusRouteRepo busRouteRepo) {
        this.busRouteRepo = busRouteRepo;
    }

    @Autowired
    public void setBusRouteRepo(BusRouteRepo busRouteRepo) {
        this.busRouteRepo = busRouteRepo;
    }

    //CREATE
    @Transactional
    @Override
    public BaseResponse<BusRoute> createBusRoute(BusRoute busRoute) {
        resetAutoIncrement();
        for(BusRoute br : busRouteRepo.findAll()){
            if(busRoute.getBusRouteName().equals(br.getBusRouteName())){
                return new BaseResponse<>(ResponseStatus.FAILED, "Tên busRouteName đã tồn tại trên hệ thống!", null);
            }
        }
        BusRoute busRouteAdd = busRouteRepo.save(busRoute);
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Tạo busroute thành công!", busRouteAdd);
    }

    //READ
    @Override
    public BaseResponse<List<BusRoute>> getAllBusRoute() {
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Lấy danh sách busroute thành công!", busRouteRepo.findAll());
    }

    @Override
    public BaseResponse<BusRoute> getBusRoute(int idBusRoute) {
        BusRoute busRoute = busRouteRepo.findById(idBusRoute).orElse(null);

        if(busRoute == null)
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy busroute có id: " + idBusRoute, null);

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã tìm thấy busroute có id: " + idBusRoute, busRoute);
    }

    //UPDATE
    @Transactional
    @Override
    public BaseResponse<BusRoute> updateBusRoute(int idBusRoute, BusRoute busRoute) {
        BaseResponse<BusRoute> baseResponse = getBusRoute(idBusRoute);

        if (baseResponse.getData() == null)
            return new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), null);

        BusRoute existingBusRoute = baseResponse.getData();

        // Nếu tên bị thay đổi
        if (!existingBusRoute.getBusRouteName().equals(busRoute.getBusRouteName())) {
            // Kiểm tra tên mới đã tồn tại chưa
            if (busRouteRepo.existsByBusRouteName(busRoute.getBusRouteName())) {
                return new BaseResponse<>(ResponseStatus.FAILED, "BusRouteName đã tồn tại", null);
            }

            // Set trạng thái bản gốc = false
            existingBusRoute.setIsAvailable(false);
            busRouteRepo.save(existingBusRoute);

            // Tạo bản mới
            BusRoute newBusRoute = new BusRoute();
            newBusRoute.setBusRouteName(busRoute.getBusRouteName());
            newBusRoute.setOverview(busRoute.getOverview());
            newBusRoute.setDescription(busRoute.getDescription());
            newBusRoute.setHighlights(busRoute.getHighlights());
            newBusRoute.setIncluded(busRoute.getIncluded());
            newBusRoute.setExcluded(busRoute.getExcluded());
            newBusRoute.setWhatToBring(busRoute.getWhatToBring());
            newBusRoute.setBeforeYouGo(busRoute.getBeforeYouGo());
            newBusRoute.setIsAvailable(true);
            newBusRoute.setIdParent(existingBusRoute.getIdBusRoute());

            BusRoute saved = busRouteRepo.save(newBusRoute);
            return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã tạo mới BusRoute thay cho bản cũ!", saved);
        }

        // Nếu tên không thay đổi → ghi đè các trường khác
        BusRoute existingBus = getBusRoute(busRoute, baseResponse);
        BusRoute updated = busRouteRepo.save(existingBus);
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã cập nhật busRoute!", updated);
    }

    private static BusRoute getBusRoute(BusRoute busRoute, BaseResponse<BusRoute> baseResponse) {
        BusRoute existingBusRoute = baseResponse.getData();

        // Cập nhật dữ liệu từ DTO
        existingBusRoute.setBusRouteName(busRoute.getBusRouteName());
        existingBusRoute.setOverview(busRoute.getOverview());
        existingBusRoute.setDescription(busRoute.getDescription());
        existingBusRoute.setHighlights(busRoute.getHighlights());
        existingBusRoute.setIncluded(busRoute.getIncluded());
        existingBusRoute.setExcluded(busRoute.getExcluded());
        existingBusRoute.setWhatToBring(busRoute.getWhatToBring());
        existingBusRoute.setBeforeYouGo(busRoute.getBeforeYouGo());
        existingBusRoute.setIsAvailable(busRoute.getIsAvailable());
        return existingBusRoute;
    }

    //DELETE
    @Transactional
    @Override
    public BaseResponse<BusRoute> deleteBusRoute(int idBusRoute) {
        BusRoute busRoute = getBusRoute(idBusRoute).getData();

        if (busRoute == null) {
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy busroute với id: " + idBusRoute, null);
        }

        try {
            busRouteRepo.deleteById(idBusRoute);
        } catch (DataIntegrityViolationException e) {
            return new BaseResponse<>(ResponseStatus.FAILED, "Lỗi ràng buộc dữ liệu!", busRoute);
        }

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Xóa thành công", busRoute);
    }

    //OTHER
    @Transactional
    @Override
    public BaseResponse<BusRoute> addListBusStopToBusRoute(int idBusRoute, List<Integer> idBusStop){
        BusRoute busRoute = getBusRoute(idBusRoute).getData();

        if(busRoute == null){
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy busRoute có id: " + idBusRoute, null);
        }

        List<BusStop> validBusStops = new ArrayList<>();

        for (Integer id : idBusStop) {
            BusStop existsBusStop = busStopService.getBusStop(id).getData();

            if (existsBusStop == null) {
                return new BaseResponse<>(ResponseStatus.FAILED,
                        "Không tìm thấy busStop có id: " + id, null);
            }

            if (existsBusStop.getBusRoute() != null) {
                return new BaseResponse<>(ResponseStatus.FAILED,
                        "BusStop này đã nằm trong busroute khác với id: " +
                                existsBusStop.getBusRoute().getIdBusRoute(), null);
            }

            validBusStops.add(existsBusStop);
        }

        // Nếu đã qua kiểm tra thì tiến hành cập nhật
        for (BusStop busStop : validBusStops) {
            busStop.setBusRoute(busRoute);
            busStopService.updateBusStop(idBusRoute, busStop);
        }

        busRoute = getBusRoute(idBusRoute).getData();

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã thêm busstop thành công vào busroute có id: " + idBusRoute, busRoute);
    }
}
