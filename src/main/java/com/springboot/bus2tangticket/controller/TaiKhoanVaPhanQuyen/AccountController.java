package com.springboot.bus2tangticket.controller.TaiKhoanVaPhanQuyen;

import com.springboot.bus2tangticket.dto.request.Account.AccountRequestDTO;
import com.springboot.bus2tangticket.dto.response.Account.AccountResponseDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TaiKhoanVaPhanQuyen.Account;
import com.springboot.bus2tangticket.service.TaiKhoanVaPhanQuyen.AccountServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class AccountController {
    @Autowired
    AccountServiceImpl accountServiceImpl;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public AccountController(AccountServiceImpl accountService) {
        this.accountServiceImpl = accountService;
    }

    @Autowired
    public void setAccountService(AccountServiceImpl accountService) {
        this.accountServiceImpl = accountService;
    }

    //CREATE
    @PostMapping("/account/{idInfo}")
    public ResponseEntity<BaseResponse<AccountResponseDTO>> createAcc(
            @PathVariable("idInfo") int idInfo,
            @Valid @RequestBody AccountRequestDTO accountRequestDTO
            ){
        BaseResponse<Account> baseResponse = accountServiceImpl.createAcc(idInfo, this.modelMapper.map(accountRequestDTO, Account.class));

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), this.modelMapper.map(baseResponse.getData(), AccountResponseDTO.class))
        );
    }

    //READ
    @GetMapping("/account")
    public ResponseEntity<BaseResponse<List<AccountResponseDTO>>> getAllAcc(){
        BaseResponse<List<Account>> baseResponse = accountServiceImpl.getAllAcc();

        List<AccountResponseDTO> dtoList = baseResponse.getData()
                .stream()
                .map(i -> this.modelMapper.map(i, AccountResponseDTO.class))
                .toList();
        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), dtoList)
        );
    }

    @GetMapping("/account/{idAcc}")
    public ResponseEntity<BaseResponse<AccountResponseDTO>> getAcc(
            @PathVariable("idAcc") int idAcc
    ){
        BaseResponse<Account> baseResponse = accountServiceImpl.getAcc(idAcc);

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        baseResponse.getData() != null
                                ? this.modelMapper.map(baseResponse.getData(), AccountResponseDTO.class)
                                : null)
        );
    }

    //UPDATE
    @PutMapping("/account/{idAcc}")
    public ResponseEntity<BaseResponse<AccountResponseDTO>> updateAcc(
            @PathVariable("idAcc") int idAcc,
            @Valid @RequestBody AccountRequestDTO accountRequestDTO
    ){
        BaseResponse<Account> baseResponse = accountServiceImpl.updateAcc(idAcc, this.modelMapper.map(accountRequestDTO, Account.class));

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        baseResponse.getData() != null
                        ? this.modelMapper.map(baseResponse.getData(), AccountResponseDTO.class)
                        : null)
        );
    }

    //DELETE
    @DeleteMapping("/account/{idAcc}")
    public ResponseEntity<BaseResponse<AccountResponseDTO>> deleteAcc(
            @PathVariable("idAcc") int idAcc
    ){
        BaseResponse<Account> baseResponse = accountServiceImpl.deleteAcc(idAcc);

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        baseResponse.getData() != null
                        ? this.modelMapper.map(baseResponse.getData(), AccountResponseDTO.class)
                        : null)
        );
    }
}
