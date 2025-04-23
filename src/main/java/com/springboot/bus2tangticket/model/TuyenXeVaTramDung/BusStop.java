package com.springboot.bus2tangticket.model.TuyenXeVaTramDung;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.bus2tangticket.model.XayDungLoTrinh.BusStopSchedule;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "busstop")
public class BusStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdBusStop")
    private Integer idBusStop;

    @Column(name = "IdParent")
    private Integer idParent;

    @Column(name = "BusStopName", nullable = false, unique = true)
    private String busStopName;

    @Column(name = "Introduction", columnDefinition = "TEXT")
    private String introduction;

    @Column(name = "Address", length = 200)
    private String address;

    @Column(name = "StopOrder", nullable = false)
    private Integer stopOrder = -1;

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

    @Column(name = "IsAvailable", nullable = false)
    private Boolean isAvailable = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdBusRoute")
    private BusRoute busRoute;

    @OneToMany(mappedBy = "busStop", fetch = FetchType.LAZY)
    private List<BusStopSchedule> busStopScheduleList;

    @OneToMany(mappedBy = "busStop", fetch = FetchType.LAZY)
    private List<MediaFile> mediaFileList;

}

