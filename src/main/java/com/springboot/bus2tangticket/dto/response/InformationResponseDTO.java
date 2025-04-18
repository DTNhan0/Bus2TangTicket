package com.springboot.bus2tangticket.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InformationResponseDTO {
    private int idInfo;
    private String cic;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dateOfBirth;
    private Boolean sex;
    private String permanentAddress;
    private String phoneNumber;
    private String email;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy HH:mm:ss",
            timezone = "Asia/Ho_Chi_Minh"
    )
    private LocalDateTime updateAt;
}
