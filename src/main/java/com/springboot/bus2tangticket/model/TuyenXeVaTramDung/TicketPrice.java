package com.springboot.bus2tangticket.model.TuyenXeVaTramDung;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticketprice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdTicketPrice")
    private Integer idTicketPrice;

    @Column(name = "ParentPrice", nullable = false)
    private BigDecimal parentPrice;

    @Column(name = "ChildPrice", nullable = false)
    private BigDecimal childPrice;

    @Column(name = "TicketType")
    private String ticketType;

    @Column(name = "UpdateAt", nullable = false)
    private LocalDateTime updateAt;

    @Column(name = "Status")
    private Boolean status = true;

    @PrePersist
    protected void onCreate() {
        if (updateAt == null) {
            updateAt = LocalDateTime.now();
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdBusRoute", nullable = false)
    private BusRoute busRoute;
}

