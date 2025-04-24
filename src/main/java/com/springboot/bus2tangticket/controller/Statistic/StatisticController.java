package com.springboot.bus2tangticket.controller.Statistic;

import com.springboot.bus2tangticket.dto.response.Statistic.RevenueStatsDTO;
import com.springboot.bus2tangticket.dto.response.Statistic.RouteStatsDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.service.Statistic.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/statistics")
public class StatisticController {

    @Autowired
    private StatisticsService statisticsService;

    /** Tổng vé & doanh thu không hủy theo tháng **/
    @GetMapping("/revenue")
    public BaseResponse<List<RevenueStatsDTO>> revenue(@RequestParam int year) {
        return statisticsService.getRevenueStats(year);
    }

    /** Tuyến đi nhiều nhất theo tháng **/
    @GetMapping("/most-traveled")
    public BaseResponse<List<RouteStatsDTO>> mostTraveled(@RequestParam int year) {
        return statisticsService.getMostTraveledStats(year);
    }

    /** Tuyến hủy nhiều nhất theo tháng **/
    @GetMapping("/most-canceled")
    public BaseResponse<List<RouteStatsDTO>> mostCanceled(@RequestParam int year) {
        return statisticsService.getMostCanceledStats(year);
    }
}