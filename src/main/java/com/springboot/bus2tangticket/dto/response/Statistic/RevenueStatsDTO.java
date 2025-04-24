package com.springboot.bus2tangticket.dto.response.Statistic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RevenueStatsDTO {
    private Integer month;
    private Long ticketCount;
    private BigDecimal totalRevenue;
}