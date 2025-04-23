package com.springboot.bus2tangticket.dto.response.UserBook;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserBookResponseDTO {

    private Integer idUserBook;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String region;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss",
            timezone = "Asia/Ho_Chi_Minh" // Thêm múi giờ [[1]][[2]]
    )
    private LocalDateTime updateAt;
}
