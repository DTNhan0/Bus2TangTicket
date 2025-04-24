package com.springboot.bus2tangticket.service.Statistic;

import com.springboot.bus2tangticket.dto.response.Statistic.RevenueStatsDTO;
import com.springboot.bus2tangticket.dto.response.Statistic.RouteStatsDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;

import java.util.List;

public interface StatisticsService {
    BaseResponse<List<RevenueStatsDTO>> getRevenueStats(int year);
    BaseResponse<List<RouteStatsDTO>> getMostTraveledStats(int year);
    BaseResponse<List<RouteStatsDTO>> getMostCanceledStats(int year);
}