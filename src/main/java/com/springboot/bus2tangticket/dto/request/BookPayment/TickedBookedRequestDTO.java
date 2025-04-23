package com.springboot.bus2tangticket.dto.request.BookPayment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TickedBookedRequestDTO {
    private Integer idRouteDepartureDate;

    private BigDecimal price;

    private int childCount;

    private int parentCount;

    private int idTicketPrice;

    private String voucherCode;
}
