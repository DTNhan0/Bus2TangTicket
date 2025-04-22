package com.springboot.bus2tangticket.model.XayDungLoTrinh;

import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "routedeparturedate")
public class RouteDepartureDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdRouteDepartureDate")
    private Integer idRouteDepartureDate;

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdBusRoute", nullable = false, foreignKey = @ForeignKey(name = "routedeparturedate_ibfk_1"))
    private BusRoute busRoute;

    @Column(name = "NumberOfSeats", nullable = false)
    private Integer numberOfSeats;

    @Column(name = "Status", nullable = false)
    private Boolean status = true;
}
