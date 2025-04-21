package com.springboot.bus2tangticket.dto.response.Account;

import com.springboot.bus2tangticket.dto.response.Information.InformationResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {

    private Integer idAccount;

    private String accountName;

    private String password;

    private Boolean isLocked;

    private InformationResponseDTO information;
}
