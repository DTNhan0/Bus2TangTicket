package com.springboot.bus2tangticket.repository;

import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStopRepo extends JpaRepository<BusStop, Integer> {
    boolean existsByBusStopName(String busStopName);
}
