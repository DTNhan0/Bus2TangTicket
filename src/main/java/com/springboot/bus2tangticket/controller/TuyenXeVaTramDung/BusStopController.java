package com.springboot.bus2tangticket.controller.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.request.BusRoute.BusRouteRequestDTO;
import com.springboot.bus2tangticket.dto.request.BusStop.BusStopRequestDTO;
import com.springboot.bus2tangticket.dto.response.BusRoute.BusRouteResponseDTO;
import com.springboot.bus2tangticket.dto.response.BusStop.BusStopResponseDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusStop;
import com.springboot.bus2tangticket.service.TuyenXeVaTramDung.BusStopServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BusStopController {
    @Autowired
    private BusStopServiceImpl busStopServiceImpl;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public BusStopController(BusStopServiceImpl busStopServiceImpl) {
        this.busStopServiceImpl = busStopServiceImpl;
    }

    @Autowired
    public void setBusStopServiceImpl(BusStopServiceImpl busStopServiceImpl) {
        this.busStopServiceImpl = busStopServiceImpl;
    }

    //CREATE
    @PostMapping("/busstop")
    public ResponseEntity<BaseResponse<BusStopResponseDTO>> createBusStop(
            @Valid @RequestBody BusStopRequestDTO requestDTO) {

        BaseResponse<BusStop> baseResponse = busStopServiceImpl.createBusStop(this.modelMapper.map(requestDTO, BusStop.class));

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), this.modelMapper.map(baseResponse.getData(), BusStopResponseDTO.class))
        );
    }

    //READ
    @GetMapping("/busstop")
    public ResponseEntity<BaseResponse<List<BusStopResponseDTO>>> getAllBusStop(){
        BaseResponse<List<BusStop>> baseResponse = busStopServiceImpl.getAllBusStop();

        List<BusStopResponseDTO> dtoList = baseResponse.getData()
                .stream()
                .map(bs -> this.modelMapper.map(bs, BusStopResponseDTO.class))
                .toList();
        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), dtoList)
        );
    }

    @GetMapping("/busstop/{idBusStop}")
    public ResponseEntity<BaseResponse<BusStopResponseDTO>> getBusStop(@PathVariable("idBusStop") int idBusStop){
        BaseResponse<BusStop> baseResponse = busStopServiceImpl.getBusStop(idBusStop);

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        baseResponse.getData() != null
                                ? this.modelMapper.map(baseResponse.getData(), BusStopResponseDTO.class)
                                : null)
        );
    }

    //UPDATE
    @PutMapping("/busstop/{idBusStop}")
    public ResponseEntity<BaseResponse<BusStopResponseDTO>> updateBusStop(
            @PathVariable("idBusStop") int idBusStop,
            @Valid @RequestBody BusStopRequestDTO updatedDto) {
        BaseResponse<BusStop> baseResponse = busStopServiceImpl.updateBusStop(idBusStop, this.modelMapper.map(updatedDto, BusStop.class));
        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        baseResponse.getData() != null
                        ? this.modelMapper.map(baseResponse.getData(), BusStopResponseDTO.class)
                        : null
                )
        );
    }

    //DELETE
    @DeleteMapping("/busstop/{idBusStop}")
    public ResponseEntity<BaseResponse<BusStopResponseDTO>> deleteBusStop(@PathVariable("idBusStop") int idBusStop){
        BaseResponse<BusStop> baseResponse = busStopServiceImpl.deleteBusStop(idBusStop);

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        baseResponse.getData() != null
                        ? this.modelMapper.map(baseResponse.getData(), BusStopResponseDTO.class)
                        : null)
        );
    }
}
