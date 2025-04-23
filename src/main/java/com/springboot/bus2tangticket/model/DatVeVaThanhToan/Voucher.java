package com.springboot.bus2tangticket.model.DatVeVaThanhToan;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "voucher", uniqueConstraints = @UniqueConstraint(columnNames = "Content"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {

    @Id
    @Column(name = "VoucherCode", length = 50)
    private String voucherCode;

    @Column(name = "Percent", nullable = false)
    private Integer percent;

    @Column(name = "Content", nullable = false, length = 150)
    private String content;

    @Column(name = "CreateDateTime", nullable = false)
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss",
            timezone = "Asia/Ho_Chi_Minh" // Thêm múi giờ [[1]][[2]]
    )
    private LocalDateTime createDateTime;

    @Column(name = "Expired", nullable = false)
    private LocalDateTime expired;

    @Column(name = "Count", nullable = false)
    private Integer count;

    @PrePersist
    public void onCreate() {
        createDateTime = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "voucher", fetch = FetchType.LAZY)
    private List<InvoiceDetail> invoiceDetails;
}

