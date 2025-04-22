package com.springboot.bus2tangticket.controller.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.request.TicketPrice.TicketPriceRequestDTO;
import com.springboot.bus2tangticket.dto.response.Account.AccountResponseDTO;
import com.springboot.bus2tangticket.dto.response.TicketPrice.TicketPriceResponseDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TaiKhoanVaPhanQuyen.Account;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.TicketPrice;
import com.springboot.bus2tangticket.service.TuyenXeVaTramDung.TicketPriceServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class TicketPriceController {

    @Autowired
    private TicketPriceServiceImpl ticketPriceService;

    @Autowired
    private ModelMapper modelMapper;

    //CREATE
    @PostMapping("/ticketprice/{idBusRoute}")
    public ResponseEntity<BaseResponse<TicketPriceResponseDTO>> createTicketPrice(
            @PathVariable("idBusRoute") int idBusRoute,
            @Valid @RequestBody TicketPriceRequestDTO requestDTO
    ) {
        TicketPrice entity = modelMapper.map(requestDTO, TicketPrice.class);
        BaseResponse<TicketPrice> base = ticketPriceService.createTicketPrice(idBusRoute, entity);
        return ResponseEntity.ok(
                new BaseResponse<>(base.getStatus(), base.getMessage(),
                        base.getData() != null
                        ? modelMapper.map(base.getData(), TicketPriceResponseDTO.class)
                        : null)
        );
    }

    //READ
    @GetMapping("/ticketprice")
    public ResponseEntity<BaseResponse<List<TicketPriceResponseDTO>>> getAllTicketPrices() {
        BaseResponse<List<TicketPrice>> baseResponse = ticketPriceService.getAllTicketPrices();

        List<TicketPriceResponseDTO> dtoList = baseResponse.getData()
                .stream()
                .map(i -> this.modelMapper.map(i, TicketPriceResponseDTO.class))
                .toList();
        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), dtoList)
        );
    }

    @GetMapping("/ticketprice/{id}")
    public ResponseEntity<BaseResponse<TicketPriceResponseDTO>> getTicketPriceById(@PathVariable int id) {
        BaseResponse<TicketPrice> baseResponse = ticketPriceService.getTicketPrice(id);

        return ResponseEntity.ok(
                new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(),
                        baseResponse.getData() != null
                                ? this.modelMapper.map(baseResponse.getData(), TicketPriceResponseDTO.class)
                                : null)
        );
    }

    //UPDATE
    @PutMapping("/ticketprice/{id}")
    public ResponseEntity<BaseResponse<TicketPriceResponseDTO>> update(
            @PathVariable int id,
            @Valid @RequestBody TicketPriceRequestDTO requestDTO
    ) {
        TicketPrice entity = modelMapper.map(requestDTO, TicketPrice.class);
        BaseResponse<TicketPrice> base = ticketPriceService.updateTicketPrice(id, entity);
        return ResponseEntity.ok(
                new BaseResponse<>(base.getStatus(), base.getMessage(),
                        base.getData() != null
                        ? modelMapper.map(base.getData(), TicketPriceResponseDTO.class)
                        : null
                )
        );
    }

    //DELETE
    @DeleteMapping("/ticketprice/{id}")
    public ResponseEntity<BaseResponse<TicketPriceResponseDTO>> delete(@PathVariable int id) {
        BaseResponse<TicketPrice> base = ticketPriceService.deleteTicketPrice(id);
        return ResponseEntity.ok(
                new BaseResponse<>(base.getStatus(), base.getMessage(),
                        base.getData() != null
                        ? modelMapper.map(base.getData(), TicketPriceResponseDTO.class)
                        : null)
        );
    }
}
