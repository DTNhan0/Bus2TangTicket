package com.springboot.bus2tangticket.service.DatVeVaThanhToan;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.Invoice;
import com.springboot.bus2tangticket.repository.InvoiceRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    @Autowired
    private InvoiceRepo invoiceRepo;

    @PersistenceContext
    private EntityManager em;

    private void resetAutoIncrement() {
        // Thiết lập AUTO_INCREMENT về 1 (tức next = max(id)+1)
        em.createNativeQuery("ALTER TABLE invoice AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    @Autowired
    public InvoiceServiceImpl(InvoiceRepo invoiceRepo) {
        this.invoiceRepo = invoiceRepo;
    }

    @Autowired
    public void setInvoiceRepo(InvoiceRepo invoiceRepo) {
        this.invoiceRepo = invoiceRepo;
    }

    @Override
    @Transactional
    public BaseResponse<Invoice> createInvoice(Invoice invoice) {
        resetAutoIncrement();
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Tạo invoice thành công!", invoiceRepo.save(invoice));
    }

    @Override
    public BaseResponse<List<Invoice>> getAllInvoice() {
        return null;
    }

    @Override
    public BaseResponse<Invoice> getInvoice(int idInvoice) {
        return null;
    }

    @Override
    @Transactional
    public BaseResponse<Invoice> updateStatusInvoice(int idInvoice, Boolean status) {
        Invoice invoice = invoiceRepo.findById(idInvoice).orElse(null);
        if(invoice == null)
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy invoice có id: " + idInvoice, null);
        invoice.setStatus(status);
        invoiceRepo.save(invoice);
        return new BaseResponse<>(ResponseStatus.FAILED, "Đã cập nhật invoice có id: " + idInvoice, invoice);
    }
}
