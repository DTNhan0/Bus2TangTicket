package com.springboot.bus2tangticket.controller.XayDungLoTrinh;

import com.springboot.bus2tangticket.dto.request.RouteDepartureDate.RouteDepartureDateRequestDTO;
import com.springboot.bus2tangticket.dto.request.RouteDepartureDate.RouteDepartureDateStatusRequestDTO;
import com.springboot.bus2tangticket.dto.response.BusRoute.BusRouteResponseDTO;
import com.springboot.bus2tangticket.dto.response.RouteDepartureDate.RouteDepartureDateListResponseDTO;
import com.springboot.bus2tangticket.dto.response.RouteDepartureDate.RouteDepartureDateResponseDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import com.springboot.bus2tangticket.model.XayDungLoTrinh.RouteDepartureDate;
import com.springboot.bus2tangticket.service.XayDungLoTrinh.RouteDepartureDateServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class RouterDepartureDateController {
    @Autowired
    RouteDepartureDateServiceImpl routerDepartureDateService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public RouterDepartureDateController(RouteDepartureDateServiceImpl routerDepartureDateService) {
        this.routerDepartureDateService = routerDepartureDateService;
    }

    @Autowired
    public void setRouterDepartureDateService(RouteDepartureDateServiceImpl routerDepartureDateService) {
        this.routerDepartureDateService = routerDepartureDateService;
    }

    //CREATE
    @PostMapping("/routerdeparturedate/{idBusRoute}")
    public ResponseEntity<BaseResponse<RouteDepartureDateResponseDTO>> createRDD(
            @PathVariable("idBusRoute") int idBusRoute,
            @RequestBody RouteDepartureDateRequestDTO requestDTO
            ){
        BaseResponse<RouteDepartureDate> baseResponse = routerDepartureDateService.createRouteDepartureDate(idBusRoute, requestDTO);

        if(baseResponse.getData() == null)
            return ResponseEntity.ok(
                    new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), null)
            );

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), this.modelMapper.map(baseResponse.getData(), RouteDepartureDateResponseDTO.class))
        );
    }

    //READ
    @GetMapping("/routerdeparturedate/{idBusRoute}")
    public ResponseEntity<BaseResponse<RouteDepartureDateListResponseDTO>> gettRDDList(
            @PathVariable("idBusRoute") int idBusRoute
    ){
        BaseResponse<BusRoute> baseResponse = routerDepartureDateService.getAllRDDByIdBusRoute(idBusRoute);

        if(baseResponse.getData() == null)
            return ResponseEntity.ok(
                    new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), null)
            );

        RouteDepartureDateListResponseDTO routeDepartureDateListResponseDTO = new RouteDepartureDateListResponseDTO();
        routeDepartureDateListResponseDTO.setBusRoute(this.modelMapper.map(baseResponse.getData(), BusRouteResponseDTO.class));
        List<RouteDepartureDateResponseDTO> dtoList = baseResponse.getData().getRouteDepartureDates()
                .stream().map(rdd -> this.modelMapper.map(rdd, RouteDepartureDateResponseDTO.class))
                .toList();

        routeDepartureDateListResponseDTO.setRouterDepartureDateResponseList(dtoList);

        return ResponseEntity.ok(
               new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), routeDepartureDateListResponseDTO)
        );
    }

    //UPDATE
    @PutMapping("/routerdeparturedate/{idRouteDepartureDate}/status")
    public ResponseEntity<BaseResponse<RouteDepartureDateResponseDTO>> updateStatus(
            @PathVariable("idRouteDepartureDate") int idRouteDepartureDate,
            @RequestBody RouteDepartureDateStatusRequestDTO dto
    ) {
        BaseResponse<com.springboot.bus2tangticket.model.XayDungLoTrinh.RouteDepartureDate> base =
                routerDepartureDateService.updateStatus(idRouteDepartureDate, dto.getStatus());

        if (base.getData() == null) {
            return ResponseEntity.ok(new BaseResponse<>(
                    base.getStatus(), base.getMessage(), null
            ));
        }

        RouteDepartureDateResponseDTO responseDTO =
                modelMapper.map(base.getData(), RouteDepartureDateResponseDTO.class);

        return ResponseEntity.ok(new BaseResponse<>(
                base.getStatus(), base.getMessage(), responseDTO
        ));
    }

    //DELETE
    @DeleteMapping("/routerdeparturedate/{idRouterDepartureDate}")
    public ResponseEntity<BaseResponse<RouteDepartureDateResponseDTO>> deleteRDD(
            @PathVariable("idRouterDepartureDate") int idRouterDepartureDate
    ){
        BaseResponse<RouteDepartureDate> baseResponse = routerDepartureDateService.deleteRouteDepartureDate(idRouterDepartureDate);

        if(baseResponse.getData() == null)
            return ResponseEntity.ok(
                    new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), null)
            );

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), this.modelMapper.map(baseResponse.getData(), RouteDepartureDateResponseDTO.class))
        );
    }
}
