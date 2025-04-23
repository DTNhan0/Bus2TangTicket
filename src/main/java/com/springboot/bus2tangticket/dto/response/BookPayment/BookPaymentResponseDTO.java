package com.springboot.bus2tangticket.dto.response.BookPayment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.bus2tangticket.dto.request.BookPayment.TickedBookedRequestDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookPaymentResponseDTO {

    private String fullName;

    private String email;

    private String phoneNumber;

    private String region;

    private TickedBookedRequestDTO TicketBooked;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss",
            timezone = "Asia/Ho_Chi_Minh" // Thêm múi giờ [[1]][[2]]
    )
    private LocalDateTime PaidDateTime;

    private Boolean PaymentMethod;

    private String PaymentVia;
}
