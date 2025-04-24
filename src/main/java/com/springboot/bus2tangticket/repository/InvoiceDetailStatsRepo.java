package com.springboot.bus2tangticket.repository;

import com.springboot.bus2tangticket.model.DatVeVaThanhToan.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceDetailStatsRepo extends JpaRepository<InvoiceDetail, Integer> {

    @Query("""
        SELECT MONTH(inv.paidDateTime) AS m,
               SUM(d.parentCount + d.childCount),
               SUM(d.price * (d.parentCount + d.childCount))
        FROM InvoiceDetail d
        JOIN d.invoice inv
        WHERE inv.status = true AND YEAR(inv.paidDateTime) = :year
        GROUP BY MONTH(inv.paidDateTime)
        """)
    List<Object[]> findRevenueStats(@Param("year") int year);

    @Query("""
        SELECT MONTH(inv.paidDateTime) AS m,
               d.routeDepartureDate.busRoute.idBusRoute,
               d.routeDepartureDate.busRoute.busRouteName,
               SUM(d.parentCount + d.childCount)
        FROM InvoiceDetail d
        JOIN d.invoice inv
        WHERE inv.status = :status AND YEAR(inv.paidDateTime) = :year
        GROUP BY MONTH(inv.paidDateTime),
                 d.routeDepartureDate.busRoute.idBusRoute,
                 d.routeDepartureDate.busRoute.busRouteName
        """)
    List<Object[]> findRouteStatsByYearAndStatus(
            @Param("year") int year,
            @Param("status") boolean status
    );
}
