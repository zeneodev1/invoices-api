package com.zeneo.invoice.controller;

import com.zeneo.invoice.dao.Invoice;
import com.zeneo.invoice.exception.InvoiceNotFoundException;
import com.zeneo.invoice.repository.InvoiceRepository;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/invoices")
@RestController
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping
    public Page<Invoice> getInvoices(@Parameter Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Invoice findInvoice(@PathVariable String id) {
        return invoiceRepository.findById(id).orElseThrow(InvoiceNotFoundException::new);
    }

    @PostMapping
    public Invoice addInvoice(@RequestBody Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @PutMapping
    public Invoice updateInvoice(@RequestBody Invoice invoice) {
        invoiceRepository.findById(invoice.getId()).orElseThrow(InvoiceNotFoundException::new);
        return invoiceRepository.save(invoice);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable String id) {
        invoiceRepository.deleteById(id);
    }
}
