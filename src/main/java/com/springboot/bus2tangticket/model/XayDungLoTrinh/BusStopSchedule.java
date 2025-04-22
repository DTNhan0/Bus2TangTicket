package com.springboot.bus2tangticket.model.XayDungLoTrinh;

import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusStop;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "busstopschedule")
public class BusStopSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDepartureTime")
    private Integer idDepartureTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdBusStop", nullable = false, foreignKey = @ForeignKey(name = "busstopschedule_ibfk_2"))
    private BusStop busStop;

    @Column(name = "OrderTime", nullable = false)
    private Integer orderTime;

    @Column(name = "Time")
    private LocalTime time;
}
