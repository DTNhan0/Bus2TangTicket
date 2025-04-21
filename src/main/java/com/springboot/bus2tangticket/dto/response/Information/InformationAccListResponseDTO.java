package com.springboot.bus2tangticket.dto.response.Information;

import com.springboot.bus2tangticket.dto.response.Account.AccountNoInfoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationAccListResponseDTO {

    private InformationResponseDTO information;

    private List<AccountNoInfoResponseDTO> accountList;
}
