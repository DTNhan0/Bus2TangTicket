package com.springboot.bus2tangticket.model.DatVeVaThanhToan;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdInvoice")
    private Integer idInvoice;

    @Column(name = "PaidDateTime", nullable = false)
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss",
            timezone = "Asia/Ho_Chi_Minh" // Thêm múi giờ [[1]][[2]]
    )
    private LocalDateTime paidDateTime;

    @PrePersist
    protected void onCreate() {
        this.paidDateTime = LocalDateTime.now(); // Không cần TimeZone, lấy thời gian local [[9]]
    }

    @PreUpdate
    protected void onUpdate() {
        this.paidDateTime = LocalDateTime.now();
    }

    @Column(name = "PaymentMethod", nullable = false)
    private Boolean paymentMethod;

    @Column(name = "PaymentVia", nullable = false, length = 50)
    private String paymentVia;

    @Column(name = "Status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
    private List<InvoiceDetail> invoiceDetails;
}

