package com.springboot.bus2tangticket.service.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.TicketPrice;
import com.springboot.bus2tangticket.repository.TicketPriceRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketPriceServiceImpl implements TicketPriceService {

    @Autowired
    private TicketPriceRepo ticketPriceRepo;

    @Autowired
    private BusRouteServiceImpl busRouteService;

    @PersistenceContext
    private EntityManager em;

    private void resetAutoIncrement() {
        em.createNativeQuery("ALTER TABLE ticketprice AUTO_INCREMENT = 1").executeUpdate();
    }

    //CREATE
    @Transactional
    @Override
    public BaseResponse<TicketPrice> createTicketPrice(int idBusRoute, TicketPrice ticketPrice) {
        resetAutoIncrement();
        BusRoute route = busRouteService.getBusRoute(idBusRoute).getData();
        if (route == null) {
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy BusRoute với id: " + idBusRoute, null);
        }

        ticketPrice.setBusRoute(route);
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Tạo ticket price thành công!", ticketPriceRepo.save(ticketPrice));
    }

    //READ
    @Override
    public BaseResponse<List<TicketPrice>> getAllTicketPrices() {
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Lấy tất cả ticket prices thành công!", ticketPriceRepo.findAll());
    }

    @Override
    public BaseResponse<TicketPrice> getTicketPrice(int id) {
        TicketPrice tp = ticketPriceRepo.findById(id).orElse(null);
        if (tp == null)
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy ticket price với id: " + id, null);
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã tìm thấy ticket price!", tp);
    }

    //UPDATE
    @Transactional
    @Override
    public BaseResponse<TicketPrice> updateTicketPrice(int id, TicketPrice ticketPrice) {
        TicketPrice existing = ticketPriceRepo.findById(id).orElse(null);
        if (existing == null)
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy ticket price với id: " + id, null);

        existing.setParentPrice(ticketPrice.getParentPrice());
        existing.setChildPrice(ticketPrice.getChildPrice());
        existing.setTicketType(ticketPrice.getTicketType());
        existing.setStatus(ticketPrice.getStatus());

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Cập nhật ticket price thành công!", existing);
    }

    //DELETE
    @Transactional
    @Override
    public BaseResponse<TicketPrice> deleteTicketPrice(int id) {
        resetAutoIncrement();
        TicketPrice tp = ticketPriceRepo.findById(id).orElse(null);
        if (tp == null)
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy ticket price với id: " + id, null);

        try {
            ticketPriceRepo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            return new BaseResponse<>(ResponseStatus.FAILED, "Không thể xóa do ràng buộc khóa ngoại!", tp);
        }

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã xóa ticket price!", tp);
    }
}

