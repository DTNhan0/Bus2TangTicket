package com.springboot.bus2tangticket.service.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import com.springboot.bus2tangticket.repository.BusRouteRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusRouteServiceImpl implements BusRouteService{

    @Autowired
    BusRouteRepo busRouteRepo;

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

        BusRoute existingBusRoute = getBusRoute(busRoute, baseResponse);

        // Kiểm tra BusRouteName đã tồn tại (và khác cái hiện tại)
        if (!existingBusRoute.getBusRouteName().equals(busRoute.getBusRouteName()) &&
                busRouteRepo.existsByBusRouteName(busRoute.getBusRouteName())) {
            return new BaseResponse<>(ResponseStatus.FAILED, "BusRouteName đã tồn tại", null);
        }

        BusRoute updated = busRouteRepo.save(existingBusRoute);

        // `updateAt` sẽ tự cập nhật qua @PreUpdate
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã cập nhật information có id: " + idBusRoute, updated);
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
}
