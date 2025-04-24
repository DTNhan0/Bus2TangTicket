package com.springboot.bus2tangticket.dto.response.DetailInvoiceResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvoiceExtendResponseDTO {

    private Integer idInvoice;

    private BigDecimal TotalPrice;

    private String fullName;

    private String phoneNumber;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss",
            timezone = "Asia/Ho_Chi_Minh" // Thêm múi giờ [[1]][[2]]
    )
    private LocalDateTime paidDateTime;

    private Boolean paymentMethod;

    private String paymentVia;

    private Boolean status;
}
