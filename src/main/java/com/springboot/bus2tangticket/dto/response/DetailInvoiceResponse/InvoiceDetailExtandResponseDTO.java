package com.springboot.bus2tangticket.dto.response.DetailInvoiceResponse;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvoiceDetailExtandResponseDTO {
    private Integer idInvoiceDetail;
    private BigDecimal price;
    private String voucherCode;
    private String busRouteName;
    private LocalDate date; //Của RouteDepartureDate
    private String ticketType;
    private Integer parentPrice;
    private Integer childPrice;
}
