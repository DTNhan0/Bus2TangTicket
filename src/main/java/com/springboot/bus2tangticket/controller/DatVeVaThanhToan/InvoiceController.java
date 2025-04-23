package com.springboot.bus2tangticket.controller.DatVeVaThanhToan;

import com.springboot.bus2tangticket.service.DatVeVaThanhToan.InvoiceServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class InvoiceController {
    @Autowired
    private InvoiceServiceImpl invoiceService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public InvoiceController(InvoiceServiceImpl invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Autowired
    public void setInvoiceService(InvoiceServiceImpl invoiceService) {
        this.invoiceService = invoiceService;
    }
}
