package com.springboot.bus2tangticket.controller;

import com.springboot.bus2tangticket.dto.request.InformationRequestDTO;
import com.springboot.bus2tangticket.dto.response.InformationResponseDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.Information;
import com.springboot.bus2tangticket.service.InformationServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class InformationController {

    @Autowired
    private InformationServiceImpl informationServiceImpl;

    @Autowired
    private ModelMapper modelMapper;

    public InformationController() {
    }

    @Autowired
    public InformationController(InformationServiceImpl informationServiceImpl) {
        this.informationServiceImpl = informationServiceImpl;
    }

    public InformationServiceImpl getInformationService() {
        return informationServiceImpl;
    }

    @Autowired
    public void setInformationService(InformationServiceImpl informationServiceImpl) {
        this.informationServiceImpl = informationServiceImpl;
    }

    //CREATE
    @PostMapping("/information")
    public ResponseEntity<BaseResponse<InformationResponseDTO>> createInfo(
            @Valid @RequestBody InformationRequestDTO requestDTO) {
        Information info = informationServiceImpl.createInfo(this.modelMapper.map(requestDTO, Information.class));
        return ResponseEntity.ok(
                new BaseResponse<>(ResponseStatus.SUCCESS, "Tạo information thành công!", this.modelMapper.map(info, InformationResponseDTO.class))
        );
    }

    //READ
    @GetMapping("/information")
    public ResponseEntity<BaseResponse<List<InformationResponseDTO>>> getInfos(){
        List<InformationResponseDTO> dtoList = informationServiceImpl.getInfos()
                                                                     .stream()
                                                                     .map(i -> this.modelMapper.map(i, InformationResponseDTO.class))
                                                                     .toList();
        return ResponseEntity.ok(
                new BaseResponse<>(ResponseStatus.SUCCESS, "Lấy danh sách information thành công!", dtoList)
        );
    }

    @GetMapping("/information/{idInfo}")
    public ResponseEntity<BaseResponse<InformationResponseDTO>> getInfo(@PathVariable int idInfo){
        Information info = informationServiceImpl.getInfo(idInfo);
        if (info == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy information có id: " + idInfo, null));
        return ResponseEntity.ok(
                new BaseResponse<>(ResponseStatus.SUCCESS, "Đã tìm thấy information có id: " + idInfo, this.modelMapper.map(info, InformationResponseDTO.class))
        );
    }

    //UPDATE
    @PutMapping("/information/{idInfo}")
    public ResponseEntity<BaseResponse<InformationResponseDTO>> updateInfo(
            @PathVariable int idInfo,
            @Valid @RequestBody InformationRequestDTO updatedDto) {
        Information updatedInfo = this.modelMapper.map(updatedDto, Information.class);
        Information updated = informationServiceImpl.updateInfo(idInfo, updatedInfo);

        if(updated == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse<>(ResponseStatus.FAILED, "Cập nhật information thất bại!", null));

        return ResponseEntity.ok(
                new BaseResponse<>(ResponseStatus.SUCCESS, "Đã cập nhật information có id: " + idInfo, this.modelMapper.map(updated, InformationResponseDTO.class))
        );
    }

    //DELETE
    @DeleteMapping("/information/{idInfo}")
    public ResponseEntity<BaseResponse<InformationResponseDTO>> deleteInfo(@PathVariable("idInfo") int idInfo){
        Information information = informationServiceImpl.deleteInfo(idInfo);
        if (information == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse<>(ResponseStatus.FAILED, "Xóa information thất bại!", null));

        return ResponseEntity.ok(
                new BaseResponse<>(ResponseStatus.SUCCESS, "Đã xóa information có id: " + idInfo, this.modelMapper.map(information, InformationResponseDTO.class))
        );
    }
}
