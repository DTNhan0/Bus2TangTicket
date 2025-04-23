package com.springboot.bus2tangticket.repository;

import com.springboot.bus2tangticket.model.DatVeVaThanhToan.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Integer> {
}
