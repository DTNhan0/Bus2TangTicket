package com.springboot.bus2tangticket.model.DatVeVaThanhToan;

import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.TicketPrice;
import com.springboot.bus2tangticket.model.XayDungLoTrinh.RouteDepartureDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "invoicedetail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdInvoiceDetail")
    private Integer idInvoiceDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdUserBook", nullable = false)
    private UserBook userBook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdRouteDepartureDate", nullable = false)
    private RouteDepartureDate routeDepartureDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdInvoice", nullable = false)
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdTicketPrice", nullable = false)
    private TicketPrice ticketPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VoucherCode")
    private Voucher voucher;

    @Column(name = "Price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "ChildCount", nullable = false)
    private Integer childCount;

    @Column(name = "ParentCount", nullable = false)
    private Integer parentCount;
}

