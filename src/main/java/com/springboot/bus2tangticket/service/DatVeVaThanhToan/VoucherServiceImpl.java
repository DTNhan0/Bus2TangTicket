package com.springboot.bus2tangticket.service.DatVeVaThanhToan;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.UserBook;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.Voucher;
import com.springboot.bus2tangticket.repository.VoucherRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService{
    @Autowired
    private VoucherRepo voucherRepo;

    @PersistenceContext
    private EntityManager em;

    private void resetAutoIncrement() {
        // Thiết lập AUTO_INCREMENT về 1 (tức next = max(id)+1)
        em.createNativeQuery("ALTER TABLE voucher AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    @Override
    @Transactional
    public BaseResponse<Voucher> createVoucher(Voucher voucher) {
        resetAutoIncrement();
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Tạo Userbook thành công!", voucherRepo.save(voucher));

    }

    @Override
    public BaseResponse<List<Voucher>> getAllVoucer() {
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Lấy danh sách userbook thành công!", voucherRepo.findAll());
    }

    @Override
    public BaseResponse<Voucher> getVoucher(String voucherCode) {
        Voucher voucher = voucherRepo.findById(voucherCode).orElse(null);

        if(voucher == null)
            return new BaseResponse<>(ResponseStatus.SUCCESS, "Không tìm thấy voucher có code: " + voucherCode, null);

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã tìm thấy userbook có code: " + voucherCode, voucher);
    }

    @Override
    public BaseResponse<Voucher> deleteVoucher(String voucherCode) {
        Voucher voucher = voucherRepo.findById(voucherCode).orElse(null);

        if(voucher == null)
            return new BaseResponse<>(ResponseStatus.SUCCESS, "Không tìm thấy voucher có code: " + voucherCode, null);

        voucherRepo.deleteById(voucherCode);

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã xóa userbook có code: " + voucherCode, voucher);
    }
}
