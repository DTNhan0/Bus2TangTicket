package com.springboot.bus2tangticket.dto.request.UserBook;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserBookRequestDTO {

    @NotNull
    private String fullName;

    @NotNull
    private String email;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String region;

}
