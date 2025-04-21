package com.springboot.bus2tangticket.dto.response.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountNoInfoResponseDTO {
    private Integer idAccount;

    private String accountName;

    private String password;

    private Boolean isLocked;
}
