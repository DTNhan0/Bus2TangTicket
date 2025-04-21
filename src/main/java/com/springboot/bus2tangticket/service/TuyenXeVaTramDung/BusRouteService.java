package com.springboot.bus2tangticket.service.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TaiKhoanVaPhanQuyen.Account;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;

import java.util.List;

public interface BusRouteService {
    BaseResponse<BusRoute> createBusRoute(BusRoute busRoute);
    BaseResponse<List<BusRoute>> getAllBusRoute();
    BaseResponse<BusRoute> getBusRoute(int idBusRoute);
    BaseResponse<BusRoute> updateBusRoute(int idBusRoute, BusRoute busRoute);
    BaseResponse<BusRoute> deleteBusRoute(int idBusRoute);
}
