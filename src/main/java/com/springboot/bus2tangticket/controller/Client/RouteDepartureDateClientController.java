package com.springboot.bus2tangticket.controller.Client;

import com.springboot.bus2tangticket.dto.response.BusRoute.BusRouteResponseDTO;
import com.springboot.bus2tangticket.dto.response.Client.RouteDepartureDateClienResponseDTO;
import com.springboot.bus2tangticket.dto.response.RouteDepartureDate.RouteDepartureDateListResponseDTO;
import com.springboot.bus2tangticket.dto.response.RouteDepartureDate.RouteDepartureDateResponseDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import com.springboot.bus2tangticket.service.XayDungLoTrinh.RouteDepartureDateServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class RouteDepartureDateClientController {

    @Autowired
    RouteDepartureDateServiceImpl routerDepartureDateService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/routerdeparturedateclient/{idBusRoute}")
    public ResponseEntity<BaseResponse<RouteDepartureDateClienResponseDTO>> gettRDDList(
            @PathVariable("idBusRoute") int idBusRoute
    ){
        BaseResponse<BusRoute> baseResponse = routerDepartureDateService.getAllRDDByIdBusRoute(idBusRoute);

        if(baseResponse.getData() == null)
            return ResponseEntity.ok(
                    new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), null)
            );

        RouteDepartureDateClienResponseDTO responseDTO = new RouteDepartureDateClienResponseDTO();

        List<RouteDepartureDateResponseDTO> dtoList = baseResponse.getData().getRouteDepartureDates()
                .stream().map(rdd -> this.modelMapper.map(rdd, RouteDepartureDateResponseDTO.class))
                .toList();

        responseDTO.setRouterDepartureDateResponseList(dtoList);

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), responseDTO)
        );
    }
}
