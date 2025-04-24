package com.springboot.bus2tangticket.repository;

import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaFileRepo extends JpaRepository<MediaFile, Integer> {
    List<MediaFile> findAllByBusRoute_IdBusRoute(Integer idBusRoute);
    List<MediaFile> findAllByBusStop_IdBusStop(Integer idBusStop);
}
