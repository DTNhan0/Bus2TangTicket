package com.springboot.bus2tangticket.service.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusStop;
import com.springboot.bus2tangticket.repository.BusStopRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusStopServiceImpl implements BusStopService{

    @Autowired
    private BusStopRepo busStopRepo;

    @PersistenceContext
    private EntityManager em;

    private void resetAutoIncrement() {
        // Thiết lập AUTO_INCREMENT về 1 (tức next = max(id)+1)
        em.createNativeQuery("ALTER TABLE busstop AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    @Autowired
    public BusStopServiceImpl(BusStopRepo busStopRepo) {
        this.busStopRepo = busStopRepo;
    }

    @Autowired
    public void setBusStopRepo(BusStopRepo busStopRepo) {
        this.busStopRepo = busStopRepo;
    }

    //CREATE
    @Transactional
    @Override
    public BaseResponse<BusStop> createBusStop(BusStop busStop) {
        resetAutoIncrement();
        for(BusStop br : busStopRepo.findAll()){
            if(busStop.getBusStopName().equals(br.getBusStopName())){
                return new BaseResponse<>(ResponseStatus.FAILED, "Tên busStopName đã tồn tại trên hệ thống!", null);
            }
        }
        BusStop busStopAdd = busStopRepo.save(busStop);
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Tạo busStop thành công!", busStopAdd);
    }

    //READ
    @Override
    public BaseResponse<List<BusStop>> getAllBusStop() {
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Lấy danh sách busStop thành công!", busStopRepo.findAll());
    }

    @Override
    public BaseResponse<BusStop> getBusStop(int idBusStop) {
        BusStop busStop = busStopRepo.findById(idBusStop).orElse(null);

        if(busStop == null)
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy busStop có id: " + idBusStop, null);

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã tìm thấy busStop có id: " + idBusStop, busStop);
    }

    //UPDATE
    @Transactional
    @Override
    public BaseResponse<BusStop> updateBusStop(int idBusStop, BusStop busStop) {
        BaseResponse<BusStop> baseResponse = getBusStop(idBusStop);

        if (baseResponse.getData() == null)
            return new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), null);

        BusStop existingBusStop = baseResponse.getData();

        // Nếu tên bị thay đổi
        if (!existingBusStop.getBusStopName().equals(busStop.getBusStopName())) {
            if (busStopRepo.existsByBusStopName(busStop.getBusStopName())) {
                return new BaseResponse<>(ResponseStatus.FAILED, "BusStopName đã tồn tại", null);
            }

            // Set bản cũ không khả dụng
            existingBusStop.setIsAvailable(false);
            busStopRepo.save(existingBusStop);

            // Tạo mới
            BusStop newBusStop = new BusStop();
            newBusStop.setBusStopName(busStop.getBusStopName());
            newBusStop.setIntroduction(busStop.getIntroduction());
            newBusStop.setAddress(busStop.getAddress());
            newBusStop.setStopOrder(busStop.getStopOrder());
            newBusStop.setIsAvailable(true);
            newBusStop.setIdParent(existingBusStop.getIdBusStop());

            BusStop saved = busStopRepo.save(newBusStop);
            return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã tạo mới BusStop thay cho bản cũ!", saved);
        }

        // Nếu không đổi tên → cập nhật như thường
        existingBusStop.setIntroduction(busStop.getIntroduction());
        existingBusStop.setAddress(busStop.getAddress());
        existingBusStop.setStopOrder(busStop.getStopOrder());
        existingBusStop.setIsAvailable(busStop.getIsAvailable());

        BusStop updated = busStopRepo.save(existingBusStop);
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã cập nhật busStop!", updated);
    }

    //DELETE
    @Transactional
    @Override
    public BaseResponse<BusStop> deleteBusStop(int idBusStop) {
        BusStop busStop = getBusStop(idBusStop).getData();

        if (busStop == null) {
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy busStop với id: " + idBusStop, null);
        }

        try {
            busStopRepo.deleteById(idBusStop);
        } catch (DataIntegrityViolationException e) {
            return new BaseResponse<>(ResponseStatus.FAILED, "Lỗi ràng buộc dữ liệu!", busStop);
        }

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Xóa thành công", busStop);
    }
}
