package com.springboot.bus2tangticket.controller.DatVeVaThanhToan;

import com.springboot.bus2tangticket.dto.request.BookPayment.BookPaymentRequestDTO;
import com.springboot.bus2tangticket.dto.response.BookPayment.BookPaymentResponseDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.service.DatVeVaThanhToan.BookPaymentService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class BookPaymentController {
    @Autowired
    private BookPaymentService bookPaymentService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/bookpayment")
    public ResponseEntity<BaseResponse<BookPaymentResponseDTO>> createBookPayment(
            @RequestBody BookPaymentRequestDTO requestDTO
            ){
        System.out.println(requestDTO);
        BaseResponse<BookPaymentResponseDTO> bookPaymentResponseDTO = bookPaymentService.createBookPayment(requestDTO);

        if(bookPaymentResponseDTO.getData() == null)
            return ResponseEntity.ok(
              new BaseResponse<>(bookPaymentResponseDTO.getStatus(), bookPaymentResponseDTO.getMessage(), null)
            );

        return ResponseEntity.ok(
                bookPaymentResponseDTO
        );
    }

}
