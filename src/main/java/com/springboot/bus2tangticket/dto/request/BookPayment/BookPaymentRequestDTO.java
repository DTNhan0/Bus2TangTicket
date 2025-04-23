package com.springboot.bus2tangticket.dto.request.BookPayment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookPaymentRequestDTO {
    @NotNull
    private String fullName;

    @NotNull
    private String email;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String region;

    @NotNull
    private TickedBookedRequestDTO ticketBooked;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd/MM/yyyy HH:mm:ss",
            timezone = "Asia/Ho_Chi_Minh" // Thêm múi giờ [[1]][[2]]
    )
    @NotNull
    private LocalDateTime paidDateTime;

    private boolean paymentMethod;

    private String paymentVia;
}
