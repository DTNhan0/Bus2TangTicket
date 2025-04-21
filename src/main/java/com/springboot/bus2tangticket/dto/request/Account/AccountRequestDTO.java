package com.springboot.bus2tangticket.dto.request.Account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountRequestDTO {

    @NotBlank(message = "Tên tài khoản không được để trống")
    @Size(min = 5, max = 50, message = "Tên tài khoản phải từ 5 đến 50 ký tự")
    private String accountName;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 5, max = 100, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @NotNull(message = "Trạng thái khóa tài khoản là bắt buộc (true/false)")
    private Boolean isLocked;

}
