package com.springboot.bus2tangticket.dto.response.InvoiceDetail;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvoiceDetailResponseDTO {

    private Integer idInvoiceDetail;

    private BigDecimal totalPrice;

    private String voucherCode;

    private String busRouteName;

    private LocalDate departureDate;

    private String ticketType;

    private Integer childCount;

    private Integer parentCount;
}
