package com.zeneo.invoice.controller;

import com.zeneo.invoice.StringUtil;
import com.zeneo.invoice.dao.Invoice;
import com.zeneo.invoice.dao.InvoiceStatus;
import com.zeneo.invoice.dao.User;
import com.zeneo.invoice.exception.InvoiceNotFoundException;
import com.zeneo.invoice.repository.InvoiceRepository;
import com.zeneo.invoice.service.InvoiceService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RequestMapping("/invoices")
@RestController
public class InvoiceController {


    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public Page<Invoice> getInvoices(@RequestParam(required = false) InvoiceStatus status,
                                     @RequestParam(defaultValue = "id") String sortBy,
                                     @RequestParam(defaultValue = "asc") String order,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "15") int size,
                                     Authentication authentication) {
        return invoiceService.getInvoices(status, sortBy, order, page, size, ((User) authentication.getPrincipal()).getId());
    }

    @GetMapping("/{id}")
    public Invoice findInvoice(@PathVariable String id, Authentication authentication) {
        return invoiceService.getInvoice(id, ((User) authentication.getPrincipal()).getId());
    }

    @PostMapping
    public Invoice addInvoice(@RequestBody Invoice invoice, Authentication authentication) {
        return invoiceService.saveInvoice(invoice, ((User) authentication.getPrincipal()).getId());
    }

    @PutMapping
    public Invoice updateInvoice(@RequestBody Invoice invoice, Authentication authentication) {
        return invoiceService.updateInvoice(invoice, ((User) authentication.getPrincipal()).getId());
    }

    @PutMapping("/status/{id}/{status}")
    public Invoice updateInvoiceStatus(@PathVariable String id, @PathVariable InvoiceStatus status, Authentication authentication) {
        return invoiceService.updateInvoiceStatus(id, status, ((User) authentication.getPrincipal()).getId());
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable String id, Authentication authentication) {
        invoiceService.deleteInvoice(id, ((User) authentication.getPrincipal()).getId());
    }
}
