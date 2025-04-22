package com.springboot.bus2tangticket.model.TuyenXeVaTramDung;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.bus2tangticket.model.TaiKhoanVaPhanQuyen.Account;
import com.springboot.bus2tangticket.model.XayDungLoTrinh.RouteDepartureDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "busroute")
public class BusRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdBusRoute")
    private Integer idBusRoute;

    @Column(name = "IdParent")
    private Integer idParent;

    @Column(name = "BusRouteName", nullable = false, unique = true)
    private String busRouteName;

    @Column(name = "Overview", columnDefinition = "TEXT")
    private String overview;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "Highlights", columnDefinition = "TEXT")
    private String highlights;

    @Column(name = "Included", columnDefinition = "TEXT")
    private String included;

    @Column(name = "Excluded", columnDefinition = "TEXT")
    private String excluded;

    @Column(name = "WhatToBring", columnDefinition = "TEXT")
    private String whatToBring;

    @Column(name = "BeforeYouGo", columnDefinition = "TEXT")
    private String beforeYouGo;

    @Column(name = "IsAvailable")
    private Boolean isAvailable;

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

    @OneToMany(mappedBy = "busRoute", fetch = FetchType.LAZY)
    private List<BusStop> busStops;

    @OneToMany(mappedBy = "busRoute", fetch = FetchType.LAZY)
    private List<TicketPrice> ticketPrices;

    @OneToMany(mappedBy = "busRoute", fetch = FetchType.LAZY)
    private List<Assignment> assignments;

    @OneToMany(mappedBy = "busRoute", fetch = FetchType.LAZY)
    private List<RouteDepartureDate> routeDepartureDates;

    public List<Account> getAssignedAccounts() {
        return assignments.stream()
                .map(Assignment::getAccount)
                .collect(Collectors.toList());
    }

}