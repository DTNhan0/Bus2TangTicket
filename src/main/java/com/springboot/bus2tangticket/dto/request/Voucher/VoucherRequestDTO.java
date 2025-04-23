package com.springboot.bus2tangticket.dto.request.Voucher;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VoucherRequestDTO {

    @NotNull
    private String voucherCode;

    @NotNull
    private Integer percent;

    @NotNull
    private String content;

    @NotNull
    private LocalDateTime expired;

    @NotNull
    private Integer count;

}
