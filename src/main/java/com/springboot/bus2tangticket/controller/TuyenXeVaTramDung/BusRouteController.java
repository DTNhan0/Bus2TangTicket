package com.springboot.bus2tangticket.controller.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.request.BusRoute.BusRouteListBusStopRequestDTO;
import com.springboot.bus2tangticket.dto.request.BusRoute.BusRouteRequestDTO;
import com.springboot.bus2tangticket.dto.response.BusRoute.BusRouteListBusStopResponseDTO;
import com.springboot.bus2tangticket.dto.response.BusRoute.BusRouteResponseDTO;
import com.springboot.bus2tangticket.dto.response.BusStop.BusStopNoBusRouteResponseDTO;
import com.springboot.bus2tangticket.dto.response.BusStop.BusStopResponseDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import com.springboot.bus2tangticket.service.TuyenXeVaTramDung.BusRouteServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class BusRouteController {
    @Autowired
    private BusRouteServiceImpl busRouteServiceImpl;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public BusRouteController(BusRouteServiceImpl busRouteServiceImpl) {
        this.busRouteServiceImpl = busRouteServiceImpl;
    }

    @Autowired
    public void setBusRouteServiceImpl(BusRouteServiceImpl busRouteServiceImpl) {
        this.busRouteServiceImpl = busRouteServiceImpl;
    }

    //CREATE
    @PostMapping("/busroute")
    public ResponseEntity<BaseResponse<BusRouteResponseDTO>> createBusRoute(
            @Valid @RequestBody BusRouteRequestDTO requestDTO) {

        BaseResponse<BusRoute> baseResponse = busRouteServiceImpl.createBusRoute(this.modelMapper.map(requestDTO, BusRoute.class));

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), baseResponse.getData() != null
                        ? this.modelMapper.map(baseResponse.getData(), BusRouteResponseDTO.class)
                        : null)
        );
    }

    //READ
    @GetMapping("/busroute")
    public ResponseEntity<BaseResponse<List<BusRouteResponseDTO>>> getAllBusRoute(){
        BaseResponse<List<BusRoute>> baseResponse = busRouteServiceImpl.getAllBusRoute();

        List<BusRouteResponseDTO> dtoList = baseResponse.getData()
                .stream()
                .map(br -> this.modelMapper.map(br, BusRouteResponseDTO.class))
                .toList();
        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), dtoList)
        );
    }

    @GetMapping("/busroute/{idBusRoute}")
    public ResponseEntity<BaseResponse<BusRouteListBusStopResponseDTO>> getBusRoute(@PathVariable("idBusRoute") int idBusRoute){
        BaseResponse<BusRoute> baseResponse = busRouteServiceImpl.getBusRoute(idBusRoute);

        if(baseResponse.getData() == null)
            return ResponseEntity.ok(
                    new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), null)
            );

        BusRouteListBusStopResponseDTO busRouteListBusStopResponseDTO = this.modelMapper.map(
                baseResponse.getData(), BusRouteListBusStopResponseDTO.class
        );

        busRouteListBusStopResponseDTO.setBusStopList(baseResponse.getData().getBusStops().stream()
                .map(bs -> this.modelMapper.map(bs, BusStopNoBusRouteResponseDTO.class))
                .toList()
        );

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        busRouteListBusStopResponseDTO)
        );
    }

    //UPDATE
    @PutMapping("/busroute/{idBusRoute}")
    public ResponseEntity<BaseResponse<BusRouteResponseDTO>> updateBusRoute(
            @PathVariable("idBusRoute") int idBusRoute,
            @Valid @RequestBody BusRouteRequestDTO updatedDto) {
        BaseResponse<BusRoute> baseResponse = busRouteServiceImpl.updateBusRoute(idBusRoute, this.modelMapper.map(updatedDto, BusRoute.class));
        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        baseResponse.getData() != null
                        ? this.modelMapper.map(baseResponse.getData(), BusRouteResponseDTO.class)
                        : null
                )
        );
    }

    //DELETE
    @DeleteMapping("/busroute/{idBusRoute}")
    public ResponseEntity<BaseResponse<BusRouteResponseDTO>> deleteBusRoute(@PathVariable("idBusRoute") int idBusRoute){
        BaseResponse<BusRoute> baseResponse = busRouteServiceImpl.deleteBusRoute(idBusRoute);

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        baseResponse.getData() != null
                        ? this.modelMapper.map(baseResponse.getData(), BusRouteResponseDTO.class)
                        : null)
        );
    }

    //OTHER
    @PutMapping("/busroute/{idBusRoute}/busstop")
    public ResponseEntity<BaseResponse<BusRouteListBusStopResponseDTO>> addListBusStopToBusRoute(
            @PathVariable("idBusRoute") int idBusRoute,
            @RequestBody BusRouteListBusStopRequestDTO busRouteListBusStopRequestDTO
    ){
        BaseResponse<BusRoute> baseResponse = busRouteServiceImpl.addListBusStopToBusRoute(idBusRoute, busRouteListBusStopRequestDTO.getIdBusStopList());

        if(baseResponse.getData() == null)
            return ResponseEntity.ok(
                    new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), null)
            );

        BusRouteListBusStopResponseDTO busRouteListBusStopResponseDTO = this.modelMapper.map(
                baseResponse.getData(), BusRouteListBusStopResponseDTO.class
        );

        busRouteListBusStopResponseDTO.setBusStopList(baseResponse.getData().getBusStops().stream()
                .map(bs -> this.modelMapper.map(bs, BusStopNoBusRouteResponseDTO.class))
                .toList()
        );

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        busRouteListBusStopResponseDTO)
        );
    }
}
