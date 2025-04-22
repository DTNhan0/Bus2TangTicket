package com.springboot.bus2tangticket.controller.TaiKhoanVaPhanQuyen;

import com.springboot.bus2tangticket.dto.request.Information.InformationRequestDTO;
import com.springboot.bus2tangticket.dto.response.Account.AccountNoInfoResponseDTO;
import com.springboot.bus2tangticket.dto.response.Information.InformationAccListResponseDTO;
import com.springboot.bus2tangticket.dto.response.Information.InformationResponseDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TaiKhoanVaPhanQuyen.Information;
import com.springboot.bus2tangticket.service.TaiKhoanVaPhanQuyen.InformationServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
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

    @Autowired
    public void setInformationService(InformationServiceImpl informationServiceImpl) {
        this.informationServiceImpl = informationServiceImpl;
    }

    //CREATE
    @PostMapping("/information")
    public ResponseEntity<BaseResponse<InformationResponseDTO>> createInfo(
            @Valid @RequestBody InformationRequestDTO requestDTO) {

        BaseResponse<Information> baseResponse = informationServiceImpl.createInfo(this.modelMapper.map(requestDTO, Information.class));

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), this.modelMapper.map(baseResponse.getData(), InformationResponseDTO.class))
        );
    }

    //READ
    @GetMapping("/information")
    public ResponseEntity<BaseResponse<List<InformationResponseDTO>>> getAllInfo(){
        BaseResponse<List<Information>> baseResponse = informationServiceImpl.getAllInfo();

        List<InformationResponseDTO> dtoList = baseResponse.getData()
                                                                     .stream()
                                                                     .map(i -> this.modelMapper.map(i, InformationResponseDTO.class))
                                                                     .toList();
        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), dtoList)
        );
    }

    @GetMapping("/information/{idInfo}")
    public ResponseEntity<BaseResponse<InformationResponseDTO>> getInfo(@PathVariable("idInfo") int idInfo){
        BaseResponse<Information> baseResponse = informationServiceImpl.getInfo(idInfo);

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        baseResponse.getData() != null
                                                ? this.modelMapper.map(baseResponse.getData(), InformationResponseDTO.class)
                                                : null)
        );
    }

    //UPDATE
    @PutMapping("/information/{idInfo}")
    public ResponseEntity<BaseResponse<InformationResponseDTO>> updateInfo(
            @PathVariable int idInfo,
            @Valid @RequestBody InformationRequestDTO updatedDto) {
        BaseResponse<Information> baseResponse = informationServiceImpl.updateInfo(idInfo, this.modelMapper.map(updatedDto, Information.class));
        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        baseResponse.getData() != null
                        ? this.modelMapper.map(baseResponse.getData(), InformationResponseDTO.class)
                        : null)
        );
    }

    //DELETE
    @DeleteMapping("/information/{idInfo}")
    public ResponseEntity<BaseResponse<InformationResponseDTO>> deleteInfo(@PathVariable("idInfo") int idInfo){
        BaseResponse<Information> baseResponse = informationServiceImpl.deleteInfo(idInfo);

        return ResponseEntity.ok(
            new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                    baseResponse.getData() != null
                    ? this.modelMapper.map(baseResponse.getData(), InformationResponseDTO.class)
                    : null)
        );
    }

    //OTHER
    //Lấy danh sách account bằng id info
    @GetMapping("/information/{idInfo}/account")
    public ResponseEntity<BaseResponse<InformationAccListResponseDTO>> getAllAccByIdInfo(@PathVariable("idInfo") int idInfo){

        BaseResponse<Information> baseResponse = informationServiceImpl.getAllAccByIdInfo(idInfo);

        Information information = baseResponse.getData();

        InformationAccListResponseDTO informationAccListResponseDTO = null;
        try {
            List<AccountNoInfoResponseDTO> dtoList = information.getAccounts().stream()
                    .map(a -> this.modelMapper.map(a, AccountNoInfoResponseDTO.class))
                    .toList();

            informationAccListResponseDTO = new InformationAccListResponseDTO();
            informationAccListResponseDTO.setInformation(this.modelMapper.map(information, InformationResponseDTO.class));
            informationAccListResponseDTO.setAccountList(dtoList);
        } catch (NullPointerException e) {
            return ResponseEntity.ok(
                    new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                            baseResponse.getData() != null
                                    ? informationAccListResponseDTO
                                    : null)
            );
        }

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), informationAccListResponseDTO)
        );
    }
}
