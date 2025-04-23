package com.springboot.bus2tangticket.service.DatVeVaThanhToan;

import com.springboot.bus2tangticket.dto.request.BookPayment.BookPaymentRequestDTO;
import com.springboot.bus2tangticket.dto.request.BookPayment.TickedBookedRequestDTO;
import com.springboot.bus2tangticket.dto.response.BookPayment.BookPaymentResponseDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.Invoice;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.InvoiceDetail;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.UserBook;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.Voucher;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.TicketPrice;
import com.springboot.bus2tangticket.model.XayDungLoTrinh.RouteDepartureDate;
import com.springboot.bus2tangticket.repository.InvoiceDetailRepo;
import com.springboot.bus2tangticket.repository.RouteDepartureDateRepo;
import com.springboot.bus2tangticket.repository.UserBookRepo;
import com.springboot.bus2tangticket.repository.VoucherRepo;
import com.springboot.bus2tangticket.service.TuyenXeVaTramDung.TicketPriceServiceImpl;
import com.springboot.bus2tangticket.service.XayDungLoTrinh.RouteDepartureDateServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookPaymentService {
    @Autowired
    private RouteDepartureDateRepo routeDepartureDateRepo;

    @Autowired
    private UserBookServiceImpl userBookService;

    @Autowired
    private UserBookRepo userBookRepo;

    @Autowired
    private VoucherServiceImpl voucherService;

    @Autowired
    private VoucherRepo voucherRepo;

    @Autowired
    private InvoiceServiceImpl invoiceService;

    @Autowired
    private InvoiceDetailRepo invoiceDetailRepo;

    @Autowired
    private TicketPriceServiceImpl ticketPriceService;

    @PersistenceContext
    private EntityManager em;

    private void resetAutoIncrement() {
        // Thiết lập AUTO_INCREMENT về 1 (tức next = max(id)+1)
        em.createNativeQuery("ALTER TABLE invoicedetail AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    @Transactional
    public BaseResponse<BookPaymentResponseDTO> createBookPayment(BookPaymentRequestDTO requestDTO) {
        BookPaymentResponseDTO dtoBaseResponse = new BookPaymentResponseDTO();
        UserBook userBook = null;

        try {
            // Xử lý UserBook
            boolean userFound = false;
            List<UserBook> userBooks = userBookService.getAllUserBook().getData();
            for (UserBook us : userBooks) {
                if (us.getPhoneNumber().equals(requestDTO.getPhoneNumber())) {
                    us.setEmail(requestDTO.getEmail());
                    us.setFullName(requestDTO.getFullName());
                    us.setRegion(requestDTO.getRegion());
                    userBook = userBookRepo.save(us);
                    userFound = true;
                    break;
                }
            }

            if (!userFound) {
                UserBook userBookAdd = new UserBook();
                userBookAdd.setPhoneNumber(requestDTO.getPhoneNumber());
                userBookAdd.setEmail(requestDTO.getEmail());
                userBookAdd.setFullName(requestDTO.getFullName());
                userBookAdd.setRegion(requestDTO.getRegion());
                userBook = userBookRepo.save(userBookAdd);
            }

            // Validate UserBook
            if (userBook == null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new BaseResponse<>(ResponseStatus.FAILED, "Lỗi khi tạo UserBook", null);
            }

            dtoBaseResponse.setFullName(userBook.getFullName());
            dtoBaseResponse.setEmail(userBook.getEmail());
            dtoBaseResponse.setPhoneNumber(userBook.getPhoneNumber());
            dtoBaseResponse.setRegion(userBook.getRegion());

            dtoBaseResponse.setTicketBooked(new TickedBookedRequestDTO());

            // Xử lý Voucher
            Voucher voucher = null;
            String voucherCode = requestDTO.getTicketBooked().getVoucherCode();
            if (voucherCode != null && !voucherCode.isEmpty()) {
                boolean voucherValid = false;
                List<Voucher> vouchers = voucherService.getAllVoucer().getData();

                for (Voucher vc : vouchers) {
                    if (vc.getVoucherCode().equals(voucherCode)) {
                        if (vc.getCount() <= 0 || vc.getExpired().isBefore(LocalDateTime.now())) {
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return new BaseResponse<>(ResponseStatus.FAILED, "Voucher không hợp lệ", null);
                        }

                        vc.setCount(vc.getCount() - 1);
                        voucher = voucherRepo.save(vc);
                        voucherValid = true;
                        break;
                    }
                }

                if (!voucherValid) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new BaseResponse<>(ResponseStatus.FAILED, "Voucher không tồn tại", null);
                }
                dtoBaseResponse.getTicketBooked().setVoucherCode(voucherCode);
            }

            // Xử lý Invoice
            Invoice invoice = new Invoice();
            invoice.setStatus(true);
            invoice.setPaymentVia(requestDTO.getPaymentVia());
            invoice.setPaymentMethod(requestDTO.isPaymentMethod());
            invoice.setPaidDateTime(requestDTO.getPaidDateTime());

            BaseResponse<Invoice> invoiceResponse = invoiceService.createInvoice(invoice);
            if (invoiceResponse.getStatus() != ResponseStatus.SUCCESS || invoiceResponse.getData() == null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new BaseResponse<>(ResponseStatus.FAILED, "Lỗi khi tạo hóa đơn", null);
            }

            Invoice invoiceAdd = invoiceResponse.getData();
            dtoBaseResponse.setPaymentMethod(invoiceAdd.getPaymentMethod());
            dtoBaseResponse.setPaymentVia(invoiceAdd.getPaymentVia());
            dtoBaseResponse.setPaidDateTime(invoiceAdd.getPaidDateTime());

            // Validate RouteDepartureDate
            RouteDepartureDate routeDepartureDate = routeDepartureDateRepo
                    .findById(requestDTO.getTicketBooked().getIdRouteDepartureDate())
                    .orElse(null);

            if (routeDepartureDate == null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy lịch trình", null);
            }

            if (!routeDepartureDate.getStatus()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new BaseResponse<>(ResponseStatus.FAILED, "Lịch trình đã bị khóa", null);
            }

            // Validate TicketPrice
            BaseResponse<TicketPrice> ticketPriceResponse = ticketPriceService
                    .getTicketPrice(requestDTO.getTicketBooked().getIdTicketPrice());

            if (ticketPriceResponse.getStatus() != ResponseStatus.SUCCESS || ticketPriceResponse.getData() == null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new BaseResponse<>(ResponseStatus.FAILED, "Loại vé không hợp lệ", null);
            }

            TicketPrice ticketPrice = ticketPriceResponse.getData();
            if (!ticketPrice.getStatus()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new BaseResponse<>(ResponseStatus.FAILED, "Loại vé đã bị khóa", null);
            }

            // Tạo InvoiceDetail
            InvoiceDetail invoiceDetail = new InvoiceDetail();
            invoiceDetail.setUserBook(userBook);
            invoiceDetail.setInvoice(invoiceAdd);
            invoiceDetail.setRouteDepartureDate(routeDepartureDate);
            invoiceDetail.setVoucher(voucher);
            invoiceDetail.setChildCount(requestDTO.getTicketBooked().getChildCount());
            invoiceDetail.setParentCount(requestDTO.getTicketBooked().getParentCount());
            invoiceDetail.setTicketPrice(ticketPrice);
            invoiceDetail.setPrice(requestDTO.getTicketBooked().getPrice());

            InvoiceDetail invoiceDetailAdd = invoiceDetailRepo.save(invoiceDetail);

            // Cập nhật response
            dtoBaseResponse.getTicketBooked().setChildCount(invoiceDetailAdd.getChildCount());
            dtoBaseResponse.getTicketBooked().setParentCount(invoiceDetailAdd.getParentCount());
            dtoBaseResponse.getTicketBooked().setPrice(invoiceDetailAdd.getPrice());
            dtoBaseResponse.getTicketBooked().setIdRouteDepartureDate(routeDepartureDate.getIdRouteDepartureDate());

            return new BaseResponse<>(ResponseStatus.SUCCESS, "Tạo hóa đơn thành công!", dtoBaseResponse);

        } catch (Exception e) {
            // Rollback cho mọi exception không xác định
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new BaseResponse<>(ResponseStatus.FAILED, "Lỗi hệ thống: " + e.getMessage(), null);
        }
    }
}

