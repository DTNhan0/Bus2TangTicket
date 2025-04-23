package com.springboot.bus2tangticket.service.DatVeVaThanhToan;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.Invoice;

import java.util.List;

public interface InvoiceService {
    BaseResponse<Invoice> createInvoice(Invoice invoice);
    BaseResponse<List<Invoice>> getAllInvoice();
    BaseResponse<Invoice> getInvoice(int idInvoice);
    BaseResponse<Invoice> updateStatusInvoice(int idInvoice, Boolean status);
}
