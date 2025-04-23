package com.springboot.bus2tangticket.service.DatVeVaThanhToan;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.InvoiceDetail;

import java.util.List;

public interface InvoiceDetailService {
    BaseResponse<InvoiceDetail> createInvoiceDetail(InvoiceDetail invoiceDetail);
    BaseResponse<List<InvoiceDetail>> getAllInvoiceDetailByIdInvoice(int idInvoice);
}
