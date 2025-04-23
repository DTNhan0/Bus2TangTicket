package com.springboot.bus2tangticket.repository;

import com.springboot.bus2tangticket.model.DatVeVaThanhToan.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceDetailRepo extends JpaRepository<InvoiceDetail, Integer> {
}
