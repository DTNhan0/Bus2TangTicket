package com.springboot.bus2tangticket.service.InvoiceExtend;

import com.springboot.bus2tangticket.dto.response.DetailInvoiceResponse.InvoiceDetailExtandResponseDTO;
import com.springboot.bus2tangticket.dto.response.DetailInvoiceResponse.InvoiceExtendResponseDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;

import java.util.List;

public interface InvoiceExtendService {
    BaseResponse<List<InvoiceExtendResponseDTO>> getAllInvoiceExtend();
    BaseResponse<List<InvoiceDetailExtandResponseDTO>> getInvoiceDetailExtend(Integer idInvoice);
    BaseResponse<InvoiceExtendResponseDTO> updateInvoiceStatus(Integer idInvoice, Boolean newStatus);
}
