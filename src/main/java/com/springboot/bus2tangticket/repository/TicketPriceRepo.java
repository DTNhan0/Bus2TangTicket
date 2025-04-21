package com.springboot.bus2tangticket.repository;

import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.TicketPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPriceRepo extends JpaRepository<TicketPrice, Integer> {
}