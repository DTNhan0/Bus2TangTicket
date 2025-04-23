package com.springboot.bus2tangticket.dto.response.Voucher;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VoucherResponseDTO {
    private String voucherCode;

    private Integer percent;

    private String content;

    private LocalDateTime expired;

    private Integer count;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss",
            timezone = "Asia/Ho_Chi_Minh" // Thêm múi giờ [[1]][[2]]
    )
    private LocalDateTime createDateTime;
}
