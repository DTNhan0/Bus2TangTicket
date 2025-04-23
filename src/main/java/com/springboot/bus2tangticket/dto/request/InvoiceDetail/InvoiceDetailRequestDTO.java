package com.springboot.bus2tangticket.dto.request.InvoiceDetail;

import com.springboot.bus2tangticket.dto.request.UserBook.UserBookRequestDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceDetailRequestDTO {

    private UserBookRequestDTO userBook;

    private Integer idRouteDepartureDate;

    //IdInvoice do Invoice tự tạo và truyền thẳng vào

    private String idTicketPrice;

    private String voucherCode;

    private BigDecimal totalPrice;

    private Integer childCount;

    private Integer parentCount;
}
