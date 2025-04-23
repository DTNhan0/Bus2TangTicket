package com.springboot.bus2tangticket.model.XayDungLoTrinh;

import com.springboot.bus2tangticket.model.DatVeVaThanhToan.InvoiceDetail;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "Status", nullable = false)
    private Boolean status = true;

    @OneToMany(mappedBy = "routeDepartureDate", fetch = FetchType.LAZY)
    private List<InvoiceDetail> invoiceDetailList;
}
