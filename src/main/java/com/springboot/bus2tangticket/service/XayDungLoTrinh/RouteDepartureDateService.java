package com.springboot.bus2tangticket.service.XayDungLoTrinh;

import com.springboot.bus2tangticket.dto.request.RouteDepartureDate.RouteDepartureDateRequestDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import com.springboot.bus2tangticket.model.XayDungLoTrinh.RouteDepartureDate;

public interface RouteDepartureDateService {
    BaseResponse<RouteDepartureDate> createRouteDepartureDate(int idBusRoute, RouteDepartureDateRequestDTO routerDepartureDate);
    BaseResponse<BusRoute> getAllRDDByIdBusRoute(int idBusRoute);
    BaseResponse<RouteDepartureDate> deleteRouteDepartureDate(int idRouterDepartureDate);
}
