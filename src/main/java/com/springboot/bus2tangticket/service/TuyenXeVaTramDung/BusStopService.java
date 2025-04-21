package com.springboot.bus2tangticket.service.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusStop;

import java.util.List;

public interface BusStopService {
    BaseResponse<BusStop> createBusStop(BusStop busStop);
    BaseResponse<List<BusStop>> getAllBusStop();
    BaseResponse<BusStop> getBusStop(int idBusStop);
    BaseResponse<BusStop> updateBusStop(int idBusStop, BusStop busStop);
    BaseResponse<BusStop> deleteBusStop(int idBusStop);
}
