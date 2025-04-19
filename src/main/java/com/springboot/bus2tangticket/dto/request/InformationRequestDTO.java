package com.springboot.bus2tangticket.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
public class InformationRequestDTO {

    @NotBlank(message = "CIC không được để trống")
    @Size(min = 12, max = 12, message = "CIC phải có 12 ký tự")
    private String cic;

    @NotBlank(message = "First name không được để trống")
    private String firstName;

    @NotBlank(message = "Middle name không được để trống")
    private String middleName;

    @NotBlank(message = "Last name không được để trống")
    private String lastName;

    @NotNull(message = "Ngày sinh không được để trống")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    private Boolean sex;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String permanentAddress;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải có 10 chữ số")
    private String phoneNumber;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;
}
