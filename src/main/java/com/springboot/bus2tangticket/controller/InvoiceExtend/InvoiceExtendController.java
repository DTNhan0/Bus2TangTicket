package com.springboot.bus2tangticket.controller.InvoiceExtend;

import com.springboot.bus2tangticket.dto.response.DetailInvoiceResponse.InvoiceDetailExtandResponseDTO;
import com.springboot.bus2tangticket.dto.response.DetailInvoiceResponse.InvoiceExtendResponseDTO;
import com.springboot.bus2tangticket.dto.response.DetailInvoiceResponse.InvoiceStatusRequestDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.service.InvoiceExtend.InvoiceExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class InvoiceExtendController {

    @Autowired
    private InvoiceExtendService extendService;

    /** Trả về danh sách tất cả hóa đơn mở rộng **/
    @GetMapping("/invoiceextend")
    public ResponseEntity<BaseResponse<List<InvoiceExtendResponseDTO>>> getAll() {
        return ResponseEntity.ok(extendService.getAllInvoiceExtend());
    }

    /** Trả về chi tiết mở rộng của một hóa đơn **/
    @GetMapping("/invoiceextend/{idInvoice}/invoicedetailextend")
    public ResponseEntity<BaseResponse<List<InvoiceDetailExtandResponseDTO>>> getDetails(
            @PathVariable Integer idInvoice) {
        return ResponseEntity.ok(extendService.getInvoiceDetailExtend(idInvoice));
    }

    @PutMapping("/invoice/{idInvoice}/status")
    public ResponseEntity<BaseResponse<InvoiceExtendResponseDTO>> updateStatus(
            @PathVariable Integer idInvoice,
            @RequestBody InvoiceStatusRequestDTO body
    ) {
        return ResponseEntity.ok(
                extendService.updateInvoiceStatus(idInvoice, body.getStatus())
        );
    }
}
