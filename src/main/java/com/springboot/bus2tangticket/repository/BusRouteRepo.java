package com.springboot.bus2tangticket.repository;

import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRouteRepo extends JpaRepository<BusRoute, Integer> {
    boolean existsByBusRouteName(String busStopName);
}
