package com.springboot.bus2tangticket.service.TuyenXeVaTramDung;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.TicketPrice;

import java.util.List;

public interface TicketPriceService {
    BaseResponse<TicketPrice> createTicketPrice(int idBusRoute, TicketPrice ticketPrice);
    BaseResponse<List<TicketPrice>> getAllTicketPrices();
    BaseResponse<TicketPrice> getTicketPrice(int id);
    BaseResponse<TicketPrice> updateTicketPrice(int id, TicketPrice ticketPrice);
    BaseResponse<TicketPrice> deleteTicketPrice(int id);
}

