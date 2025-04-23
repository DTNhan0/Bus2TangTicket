package com.springboot.bus2tangticket.controller.DatVeVaThanhToan;

import com.springboot.bus2tangticket.dto.request.UserBook.UserBookRequestDTO;
import com.springboot.bus2tangticket.dto.response.UserBook.UserBookResponseDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.UserBook;
import com.springboot.bus2tangticket.service.DatVeVaThanhToan.UserBookServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class UserBookController {
    @Autowired
    UserBookServiceImpl userBookService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public UserBookController(UserBookServiceImpl userBookService) {
        this.userBookService = userBookService;
    }

    @Autowired
    public void setUserBookService(UserBookServiceImpl userBookService) {
        this.userBookService = userBookService;
    }

    @PostMapping("/userbook")
    public ResponseEntity<BaseResponse<UserBookResponseDTO>> createUserbook(
            @Valid @RequestBody UserBookRequestDTO requestDTO
            ){
        UserBook userBook = this.modelMapper.map(requestDTO, UserBook.class);
        BaseResponse<UserBook> baseResponse = userBookService.createUserBook(userBook);

        if(baseResponse.getData() == null)
            return ResponseEntity.ok(
                   new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), null)
            );

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        this.modelMapper.map(baseResponse.getData(), UserBookResponseDTO.class))
        );
    }

    @GetMapping("/userbook")
    public ResponseEntity<BaseResponse<List<UserBookResponseDTO>>> getAllUserBook(){
        BaseResponse<List<UserBook>> baseResponse = userBookService.getAllUserBook();

        if(baseResponse.getData() == null)
            return ResponseEntity.ok(
                    new BaseResponse<>(ResponseStatus.FAILED, "Danh sách rỗng!", null)
            );

        List<UserBookResponseDTO> userBookResponseDTOS = baseResponse.getData().stream()
                .map(ub -> this.modelMapper.map(ub, UserBookResponseDTO.class))
                .toList();

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), userBookResponseDTOS)
        );
    }

    @GetMapping("/userbook/{idUserBook}")
    public ResponseEntity<BaseResponse<UserBookResponseDTO>> getUserBook(
            @PathVariable int idUserBook
    ){
        BaseResponse<UserBook> baseResponse = userBookService.getUserBook(idUserBook);

        if(baseResponse.getData() == null)
            return ResponseEntity.ok(
                    new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), null)
            );

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        this.modelMapper.map(baseResponse.getData(), UserBookResponseDTO.class))
        );
    }
}
