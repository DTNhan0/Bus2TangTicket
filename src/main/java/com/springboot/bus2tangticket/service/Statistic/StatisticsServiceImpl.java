package com.springboot.bus2tangticket.service.Statistic;

import com.springboot.bus2tangticket.dto.response.Statistic.RevenueStatsDTO;
import com.springboot.bus2tangticket.dto.response.Statistic.RouteStatsDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.repository.InvoiceDetailStatsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private InvoiceDetailStatsRepo detailStatsRepo;

    @Override
    public BaseResponse<List<RevenueStatsDTO>> getRevenueStats(int year) {
        List<Object[]> raw = detailStatsRepo.findRevenueStats(year);
        List<RevenueStatsDTO> out = raw.stream()
                .map(r -> new RevenueStatsDTO(
                        (Integer) r[0],
                        ((Number) r[1]).longValue(),
                        (BigDecimal) r[2]
                ))
                .sorted(Comparator.comparing(RevenueStatsDTO::getMonth))
                .collect(Collectors.toList());

        return new BaseResponse<>(
                ResponseStatus.SUCCESS,
                "Thống kê doanh thu năm " + year + " theo tháng",
                out
        );
    }

    private List<RouteStatsDTO> topPerMonth(List<Object[]> raw) {
        // raw: [ month, routeId, routeName, count ]
        Map<Integer, RouteStatsDTO> best = new HashMap<>();
        for (Object[] r : raw) {
            Integer m = (Integer) r[0];
            Integer rid = (Integer) r[1];
            String name = (String) r[2];
            Long cnt = ((Number) r[3]).longValue();
            RouteStatsDTO dto = new RouteStatsDTO(m, rid, name, cnt);
            best.merge(m, dto, (old, neu) ->
                    neu.getPassengerCount() > old.getPassengerCount() ? neu : old
            );
        }
        return best.values().stream()
                .sorted(Comparator.comparing(RouteStatsDTO::getMonth))
                .collect(Collectors.toList());
    }

    @Override
    public BaseResponse<List<RouteStatsDTO>> getMostTraveledStats(int year) {
        List<Object[]> raw = detailStatsRepo.findRouteStatsByYearAndStatus(year, true);
        List<RouteStatsDTO> out = topPerMonth(raw);
        return new BaseResponse<>(
                ResponseStatus.SUCCESS,
                "Thống kê tuyến đi nhiều nhất năm " + year + " theo tháng",
                out
        );
    }

    @Override
    public BaseResponse<List<RouteStatsDTO>> getMostCanceledStats(int year) {
        List<Object[]> raw = detailStatsRepo.findRouteStatsByYearAndStatus(year, false);
        List<RouteStatsDTO> out = topPerMonth(raw);
        return new BaseResponse<>(
                ResponseStatus.SUCCESS,
                "Thống kê tuyến bị hủy nhiều nhất năm " + year + " theo tháng",
                out
        );
    }
}
