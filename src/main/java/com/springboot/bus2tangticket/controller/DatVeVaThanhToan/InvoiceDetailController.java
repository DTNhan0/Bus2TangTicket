package com.springboot.bus2tangticket.controller.DatVeVaThanhToan;

import com.springboot.bus2tangticket.service.DatVeVaThanhToan.InvoiceDetailServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class InvoiceDetailController {
    @Autowired
    private InvoiceDetailServiceImpl invoiceDetailService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public InvoiceDetailController(InvoiceDetailServiceImpl invoiceDetailService) {
        this.invoiceDetailService = invoiceDetailService;
    }

    @Autowired
    public void setInvoiceDetailService(InvoiceDetailServiceImpl invoiceDetailService) {
        this.invoiceDetailService = invoiceDetailService;
    }
}
