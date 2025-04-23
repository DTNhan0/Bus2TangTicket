package com.springboot.bus2tangticket.controller.DatVeVaThanhToan;

import com.springboot.bus2tangticket.dto.request.Voucher.VoucherRequestDTO;
import com.springboot.bus2tangticket.dto.response.Voucher.VoucherResponseDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.Voucher;
import com.springboot.bus2tangticket.service.DatVeVaThanhToan.VoucherServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class VoucherController {
    @Autowired
    VoucherServiceImpl voucherService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public VoucherController(VoucherServiceImpl voucherService) {
        this.voucherService = voucherService;
    }

    @Autowired
    public void setVoucherService(VoucherServiceImpl voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/voucher")
    public ResponseEntity<BaseResponse<VoucherResponseDTO>> createVoucher(
            @Valid @RequestBody VoucherRequestDTO voucherRequestDTO
            ){
        Voucher voucher = this.modelMapper.map(voucherRequestDTO, Voucher.class);

        BaseResponse<Voucher> baseResponse = voucherService.createVoucher(voucher);

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        this.modelMapper.map(baseResponse.getData(), VoucherResponseDTO.class))
        );
    }

    @GetMapping("/voucher")
    public ResponseEntity<BaseResponse<List<VoucherResponseDTO>>> getAllVoucher(){
        BaseResponse<List<Voucher>> baseResponse = voucherService.getAllVoucer();

        if(baseResponse.getData() == null)
            return ResponseEntity.ok(
                    new BaseResponse<>(ResponseStatus.FAILED, "Danh sách rỗng!",
                            null)
            );

        List<VoucherResponseDTO> dtos = baseResponse.getData().stream()
                .map(v -> this.modelMapper.map(v, VoucherResponseDTO.class))
                .toList();

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        dtos)
        );
    }

    @GetMapping("/voucher/{codeVoucher}")
    public ResponseEntity<BaseResponse<VoucherResponseDTO>> getVoucherByCode(
            @PathVariable("codeVoucher") String codeVoucher
    ){
        BaseResponse<Voucher> baseResponse = voucherService.getVoucher(codeVoucher);

        if(baseResponse.getData() == null)
            return ResponseEntity.ok(
                    new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                            null)
            );

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        this.modelMapper.map(baseResponse.getData(), VoucherResponseDTO.class))
        );
    }

    @DeleteMapping("/voucher/{codeVoucher}")
    public ResponseEntity<BaseResponse<VoucherResponseDTO>> deleteVoucherByCode(
            @PathVariable("codeVoucher") String codeVoucher
    ){
        BaseResponse<Voucher> baseResponse = voucherService.deleteVoucher(codeVoucher);

        if(baseResponse.getData() == null)
            return ResponseEntity.ok(
                    new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                            null)
            );

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        this.modelMapper.map(baseResponse.getData(), VoucherResponseDTO.class))
        );
    }
}
