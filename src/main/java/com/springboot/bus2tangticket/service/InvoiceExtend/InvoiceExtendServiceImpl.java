package com.springboot.bus2tangticket.service.InvoiceExtend;

import com.springboot.bus2tangticket.dto.response.DetailInvoiceResponse.InvoiceDetailExtandResponseDTO;
import com.springboot.bus2tangticket.dto.response.DetailInvoiceResponse.InvoiceExtendResponseDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.Invoice;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.InvoiceDetail;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.UserBook;
import com.springboot.bus2tangticket.repository.InvoiceDetailRepo;
import com.springboot.bus2tangticket.repository.InvoiceRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceExtendServiceImpl implements InvoiceExtendService {

    @Autowired private InvoiceRepo invoiceRepo;
    @Autowired private InvoiceDetailRepo detailRepo;

    @Override
    public BaseResponse<List<InvoiceExtendResponseDTO>> getAllInvoiceExtend() {
        List<InvoiceExtendResponseDTO> list = invoiceRepo.findAll().stream().map(inv -> {
            List<InvoiceDetail> details = detailRepo.findByInvoice_IdInvoice(inv.getIdInvoice());

            // Tính tổng giá
            BigDecimal total = details.stream()
                    .map(d -> d.getPrice()
                            .multiply(BigDecimal.valueOf(d.getParentCount() + d.getChildCount())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Lấy thông tin userbook (nếu có)
            String fullName = null, phone = null;
            if (!details.isEmpty() && details.get(0).getUserBook() != null) {
                UserBook ub = details.get(0).getUserBook();
                fullName = ub.getFullName();
                phone    = ub.getPhoneNumber();
            }

            InvoiceExtendResponseDTO dto = new InvoiceExtendResponseDTO();
            dto.setIdInvoice(inv.getIdInvoice());
            dto.setTotalPrice(total);
            dto.setFullName(fullName);
            dto.setPhoneNumber(phone);
            dto.setPaidDateTime(inv.getPaidDateTime());
            dto.setPaymentMethod(inv.getPaymentMethod());
            dto.setPaymentVia(inv.getPaymentVia());
            dto.setStatus(inv.getStatus());
            return dto;
        }).collect(Collectors.toList());

        return new BaseResponse<>(
                ResponseStatus.SUCCESS,
                "Lấy danh sách hóa đơn thành công!",
                list
        );
    }

    @Override
    public BaseResponse<List<InvoiceDetailExtandResponseDTO>> getInvoiceDetailExtend(Integer idInvoice) {
        if (!invoiceRepo.existsById(idInvoice)) {
            return new BaseResponse<>(
                    ResponseStatus.FAILED,
                    "Không tìm thấy hóa đơn id: " + idInvoice,
                    null
            );
        }

        List<InvoiceDetailExtandResponseDTO> list = detailRepo.findByInvoice_IdInvoice(idInvoice)
                .stream()
                .map(d -> {
                    InvoiceDetailExtandResponseDTO dto = new InvoiceDetailExtandResponseDTO();
                    dto.setIdInvoiceDetail(d.getIdInvoiceDetail());
                    dto.setPrice(d.getPrice());
                    dto.setVoucherCode(d.getVoucher() != null
                            ? d.getVoucher().getVoucherCode() : null);
                    dto.setBusRouteName(d.getRouteDepartureDate()
                            .getBusRoute().getBusRouteName());
                    dto.setDate(d.getRouteDepartureDate().getDate());
                    dto.setTicketType(d.getTicketPrice().getTicketType());
                    dto.setParentPrice(d.getParentCount());
                    dto.setChildPrice(d.getChildCount());
                    return dto;
                })
                .collect(Collectors.toList());

        return new BaseResponse<>(
                ResponseStatus.SUCCESS,
                "Lấy chi tiết hóa đơn thành công!",
                list
        );
    }

    @Override
    @Transactional
    public BaseResponse<InvoiceExtendResponseDTO> updateInvoiceStatus(Integer idInvoice, Boolean newStatus) {
        Invoice inv = invoiceRepo.findById(idInvoice).orElse(null);
        if (inv == null) {
            return new BaseResponse<>(
                    ResponseStatus.FAILED,
                    "Không tìm thấy hóa đơn id: " + idInvoice,
                    null
            );
        }
        // Cập nhật và lưu
        inv.setStatus(newStatus);
        invoiceRepo.save(inv);

        // Tính lại tổng giá và lấy thông tin user
        List<InvoiceDetail> details = detailRepo.findByInvoice_IdInvoice(idInvoice);
        BigDecimal total = details.stream()
                .map(d -> d.getPrice().multiply(BigDecimal.valueOf(d.getParentCount() + d.getChildCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String fullName = null, phone = null;
        if (!details.isEmpty() && details.get(0).getUserBook() != null) {
            UserBook ub = details.get(0).getUserBook();
            fullName = ub.getFullName();
            phone    = ub.getPhoneNumber();
        }

        InvoiceExtendResponseDTO dto = new InvoiceExtendResponseDTO();
        dto.setIdInvoice(inv.getIdInvoice());
        dto.setTotalPrice(total);
        dto.setFullName(fullName);
        dto.setPhoneNumber(phone);
        dto.setPaidDateTime(inv.getPaidDateTime());
        dto.setPaymentMethod(inv.getPaymentMethod());
        dto.setPaymentVia(inv.getPaymentVia());
        dto.setStatus(inv.getStatus());

        return new BaseResponse<>(
                ResponseStatus.SUCCESS,
                "Cập nhật trạng thái hóa đơn thành công!",
                dto
        );
    }
}
