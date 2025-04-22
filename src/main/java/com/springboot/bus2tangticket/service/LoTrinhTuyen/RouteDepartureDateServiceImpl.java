package com.springboot.bus2tangticket.service.LoTrinhTuyen;


import com.springboot.bus2tangticket.dto.request.RouteDepartureDate.RouteDepartureDateRequestDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import com.springboot.bus2tangticket.model.XayDungLoTrinh.RouteDepartureDate;
import com.springboot.bus2tangticket.repository.RouteDepartureDateRepo;
import com.springboot.bus2tangticket.service.TuyenXeVaTramDung.BusRouteServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteDepartureDateServiceImpl implements RouteDepartureDateService {
    @Autowired
    RouteDepartureDateRepo routeDepartureDateRepo;

    @Autowired
    BusRouteServiceImpl busRouteService;

    @PersistenceContext
    private EntityManager em;

    private void resetAutoIncrement() {
        // Thiết lập AUTO_INCREMENT về 1 (tức next = max(id)+1)
        em.createNativeQuery("ALTER TABLE routedeparturedate AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    @Autowired
    public RouteDepartureDateServiceImpl(RouteDepartureDateRepo routeDepartureDate) {
        this.routeDepartureDateRepo = routeDepartureDate;
    }

    @Autowired
    public void setRouterDepartureDateRepo(RouteDepartureDateRepo routeDepartureDate) {
        this.routeDepartureDateRepo = routeDepartureDate;
    }

    //CREATE
    @Transactional
    @Override
    public BaseResponse<RouteDepartureDate> createRouteDepartureDate(int idBusRoute, RouteDepartureDateRequestDTO routerDepartureDate) {
        resetAutoIncrement();

        BusRoute busRoute = busRouteService.getBusRoute(idBusRoute).getData();

        if(busRoute == null)
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy BusRoute có id: " + idBusRoute, null);

        com.springboot.bus2tangticket.model.XayDungLoTrinh.RouteDepartureDate routeDepartureDateAdd = new com.springboot.bus2tangticket.model.XayDungLoTrinh.RouteDepartureDate();
        routeDepartureDateAdd.setBusRoute(busRoute);
        routeDepartureDateAdd.setDate(routerDepartureDate.getDate());
        routeDepartureDateAdd.setStatus(routerDepartureDate.getStatus());
        routeDepartureDateAdd.setNumberOfSeats(routerDepartureDate.getNumberOfSeats());

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Tạo routerdeparturedate thành công!", routeDepartureDateRepo.save(routeDepartureDateAdd));
    }

    //READ
    @Override
    public BaseResponse<BusRoute> getAllRDDByIdBusRoute(int idBusRoute) {
        BusRoute busRoute = busRouteService.getBusRoute(idBusRoute).getData();

        if(busRoute == null)
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy BusRoute có id: " + idBusRoute, null);

        busRoute.getRouteDepartureDates();

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Lấy danh sách routerdeparturedate thành công!", busRoute);
    }

    //UPDATE

    //DELETE
    @Override
    public BaseResponse<RouteDepartureDate> deleteRouteDepartureDate(int idRouterDepartureDate) {
       RouteDepartureDate routeDepartureDate = routeDepartureDateRepo.findById(idRouterDepartureDate).orElse(null);

        if(routeDepartureDate == null)
            return new BaseResponse<>(ResponseStatus.FAILED, "Xóa routerdeparturedate thất bại có id: " + idRouterDepartureDate, null);

        routeDepartureDateRepo.deleteById(idRouterDepartureDate);

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Xóa routerdeparturedate thành công có id: " + idRouterDepartureDate, routeDepartureDate);

    }
}
