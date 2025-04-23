package com.springboot.bus2tangticket.service.DatVeVaThanhToan;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.Voucher;

import java.util.List;

public interface VoucherService {
    BaseResponse<Voucher> createVoucher(Voucher voucher);
    BaseResponse<List<Voucher>> getAllVoucer();
    BaseResponse<Voucher> getVoucher(String voucherCode);
    BaseResponse<Voucher> deleteVoucher(String voucherCode);
}
