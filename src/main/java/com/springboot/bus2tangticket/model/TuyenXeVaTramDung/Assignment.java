package com.springboot.bus2tangticket.model.TuyenXeVaTramDung;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.bus2tangticket.model.TaiKhoanVaPhanQuyen.Account;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "assignment",
        uniqueConstraints = @UniqueConstraint(name = "unique_assignment", columnNames = {"IdBusRoute", "IdAccount"}))
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdAssignment")
    private Integer idAssignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdBusRoute", nullable = false, foreignKey = @ForeignKey(name = "assignment_ibfk_1"))
    private BusRoute busRoute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdAccount", nullable = false, foreignKey = @ForeignKey(name = "assignment_ibfk_2"))
    private Account account;

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
}

