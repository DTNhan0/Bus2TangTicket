package com.springboot.bus2tangticket.model.DatVeVaThanhToan;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "userbook", uniqueConstraints = @UniqueConstraint(columnNames = "PhoneNumber"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdUserBook")
    private Integer idUserBook;

    @Column(name = "FullName", nullable = false)
    private String fullName;

    @Column(name = "Email", nullable = false, length = 100)
    private String email;

    @Column(name = "PhoneNumber", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "Region", nullable = false, length = 100)
    private String region;

    @Column(name = "UpdateAt", nullable = false)
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss",
            timezone = "Asia/Ho_Chi_Minh" // Thêm múi giờ [[1]][[2]]
    )
    private LocalDateTime updateAt;

    @PrePersist
    protected void onCreate() {
        this.updateAt = LocalDateTime.now(); // Không cần TimeZone, lấy thời gian local [[9]]
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "userBook", fetch = FetchType.LAZY)
    private List<InvoiceDetail> invoiceDetails;
}

