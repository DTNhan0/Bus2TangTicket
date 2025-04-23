package com.springboot.bus2tangticket.service.DatVeVaThanhToan;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.InvoiceDetail;
import com.springboot.bus2tangticket.repository.InvoiceDetailRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceDetailServiceImpl implements InvoiceDetailService{

    @Autowired
    private InvoiceDetailRepo invoiceDetailRepo;

    @PersistenceContext
    private EntityManager em;

    private void resetAutoIncrement() {
        // Thiết lập AUTO_INCREMENT về 1 (tức next = max(id)+1)
        em.createNativeQuery("ALTER TABLE invoice AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    @Autowired
    public InvoiceDetailServiceImpl(InvoiceDetailRepo invoiceDetailRepo) {
        this.invoiceDetailRepo = invoiceDetailRepo;
    }

    @Autowired
    public void setInvoiceDetailRepo(InvoiceDetailRepo invoiceDetailRepo) {
        this.invoiceDetailRepo = invoiceDetailRepo;
    }

    @Override
    public BaseResponse<InvoiceDetail> createInvoiceDetail(InvoiceDetail invoiceDetail) {
        return null;
    }

    @Override
    public BaseResponse<List<InvoiceDetail>> getAllInvoiceDetailByIdInvoice(int idInvoice) {
        return null;
    }
}
