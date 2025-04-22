package com.springboot.bus2tangticket.controller.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.request.Assignment.AssignmentRequestDTO;
import com.springboot.bus2tangticket.dto.response.Account.AccountNoInfoResponseDTO;
import com.springboot.bus2tangticket.dto.response.Assignment.AssignmentResponseDTO;
import com.springboot.bus2tangticket.dto.response.BusRoute.BusRouteResponseDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.TaiKhoanVaPhanQuyen.Account;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.Assignment;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import com.springboot.bus2tangticket.service.TuyenXeVaTramDung.AssignmentServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1")
public class AssignmentController {
    @Autowired
    AssignmentServiceImpl assignmentService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public AssignmentController(AssignmentServiceImpl assignmentService) {
        this.assignmentService = assignmentService;
    }

    @Autowired
    public void setAssignmentService(AssignmentServiceImpl assignmentService) {
        this.assignmentService = assignmentService;
    }

    //CREATE
    @PostMapping("/assignment/{idBusRoute}")
    public ResponseEntity<BaseResponse<AssignmentResponseDTO>> createAssignment(
            @PathVariable("idBusRoute") int idBusRoute,
            @Valid @RequestBody AssignmentRequestDTO assignmentRequestDTO
            ){

        BaseResponse<List<Assignment>> baseResponse = assignmentService.createAssignment(idBusRoute, assignmentRequestDTO.getIdAccountList());

        if(baseResponse.getData() == null)
            return ResponseEntity.ok(
                    new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), null)
            );

        BusRoute busRoute = baseResponse.getData().getFirst().getBusRoute();
        BusRouteResponseDTO busRouteResponseDTO = this.modelMapper.map(busRoute, BusRouteResponseDTO.class);

        List<Account> accountList = new ArrayList<>();

        for(Assignment assignment : baseResponse.getData())
            accountList.add(assignment.getAccount());

        List<AccountNoInfoResponseDTO> accountNoInfoResponseDTOS = accountList.stream()
                .map(a -> this.modelMapper.map(a, AccountNoInfoResponseDTO.class))
                .toList();

        AssignmentResponseDTO assignmentResponseDTO = new AssignmentResponseDTO();
        assignmentResponseDTO.setBusRoute(busRouteResponseDTO);
        assignmentResponseDTO.setAccountList(accountNoInfoResponseDTOS);

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), assignmentResponseDTO)
        );
    }

    //READ

    //UPDATE

    //DELETE
    @DeleteMapping("/assignment/{idBusRoute}")
    public ResponseEntity<BaseResponse<AssignmentResponseDTO>> deleteAssignment(
            @PathVariable("idBusRoute") int idBusRoute,
            @RequestBody AssignmentRequestDTO assignmentRequestDTO
    ){
        BaseResponse<List<Assignment>> baseResponse = assignmentService.deleteAssignment(idBusRoute, assignmentRequestDTO.getIdAccountList());

        if(baseResponse.getData() == null)
            return ResponseEntity.ok(
                    new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), null)
            );

        try {
            baseResponse.getData().getFirst();
        } catch (NoSuchElementException e) {
            return ResponseEntity.ok(
                    new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy id account cần xóa!", null)
            );
        }

        BusRoute busRoute = baseResponse.getData().getFirst().getBusRoute();

        BusRouteResponseDTO busRouteResponseDTO = this.modelMapper.map(busRoute, BusRouteResponseDTO.class);

        List<Account> accountList = new ArrayList<>();

        for(Assignment assignment : baseResponse.getData())
            accountList.add(assignment.getAccount());

        List<AccountNoInfoResponseDTO> accountNoInfoResponseDTOS = accountList.stream()
                .map(a -> this.modelMapper.map(a, AccountNoInfoResponseDTO.class))
                .toList();

        AssignmentResponseDTO assignmentResponseDTO = new AssignmentResponseDTO();
        assignmentResponseDTO.setBusRoute(busRouteResponseDTO);
        assignmentResponseDTO.setAccountList(accountNoInfoResponseDTOS);

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), assignmentResponseDTO)
        );
    }

    //OTHER
    @GetMapping("/assignment/{idBusRoute}/account")
    public ResponseEntity<BaseResponse<AssignmentResponseDTO>> getAssignmentAccList(
            @PathVariable("idBusRoute") int idBusRoute
    ){
        BaseResponse<List<Assignment>> baseResponse = assignmentService.getAssignmentAccList(idBusRoute);

        if(baseResponse.getData() == null)
            return ResponseEntity.ok(
                    new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), null)
            );

        BusRoute busRoute = baseResponse.getData().getFirst().getBusRoute();
        BusRouteResponseDTO busRouteResponseDTO = this.modelMapper.map(busRoute, BusRouteResponseDTO.class);

        List<Account> accountList = new ArrayList<>();

        for(Assignment assignment : baseResponse.getData())
            accountList.add(assignment.getAccount());

        List<AccountNoInfoResponseDTO> accountNoInfoResponseDTOS = accountList.stream()
                .map(a -> this.modelMapper.map(a, AccountNoInfoResponseDTO.class))
                .toList();

        AssignmentResponseDTO assignmentResponseDTO = new AssignmentResponseDTO();
        assignmentResponseDTO.setBusRoute(busRouteResponseDTO);
        assignmentResponseDTO.setAccountList(accountNoInfoResponseDTOS);

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), assignmentResponseDTO)
        );
    }
}
