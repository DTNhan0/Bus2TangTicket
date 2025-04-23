package com.springboot.bus2tangticket.controller.Client;

import com.springboot.bus2tangticket.dto.response.Client.ClientResponseBusRouteDTO;
import com.springboot.bus2tangticket.dto.response.Client.ClientResponseListBusRouteDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.service.Client.BusRouteClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class BusRouteClientController {
    @Autowired
    private BusRouteClientService busRouteClientService;

    @Autowired
    public BusRouteClientController(BusRouteClientService busRouteClientService) {
        this.busRouteClientService = busRouteClientService;
    }

    @Autowired
    public void setBusRouteClientService(BusRouteClientService busRouteClientService) {
        this.busRouteClientService = busRouteClientService;
    }

    @GetMapping("/busroute/hcm")
    public ResponseEntity<BaseResponse<List<ClientResponseListBusRouteDTO>>> getClientListBusRoute(){
        BaseResponse<List<ClientResponseListBusRouteDTO>> baseResponse = busRouteClientService.getClientListBusRoute();
        return ResponseEntity.ok(
                baseResponse
        );
    }

    @GetMapping("/busroute/hcm/{idBusRoute}")
    public ResponseEntity<BaseResponse<ClientResponseBusRouteDTO>> getClientBusRoute(
            @PathVariable("idBusRoute") int idBusRoute
    ){
        BaseResponse<ClientResponseBusRouteDTO> baseResponse = busRouteClientService.getClientBusRoute(idBusRoute);
        return ResponseEntity.ok(
                baseResponse
        );
    }
}
