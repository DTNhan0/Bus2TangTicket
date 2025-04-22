package com.springboot.bus2tangticket.dto.response.Assignment;

import com.springboot.bus2tangticket.dto.response.Account.AccountNoInfoResponseDTO;
import com.springboot.bus2tangticket.dto.response.BusRoute.BusRouteResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class
AssignmentResponseDTO {

    private BusRouteResponseDTO busRoute;

    private List<AccountNoInfoResponseDTO>  accountList;

}
