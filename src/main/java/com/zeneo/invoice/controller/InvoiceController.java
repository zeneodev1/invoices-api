package com.zeneo.invoice.controller;

import com.zeneo.invoice.StringUtil;
import com.zeneo.invoice.dao.Invoice;
import com.zeneo.invoice.dao.InvoiceStatus;
import com.zeneo.invoice.dao.User;
import com.zeneo.invoice.exception.InvoiceNotFoundException;
import com.zeneo.invoice.repository.InvoiceRepository;
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
    private InvoiceRepository invoiceRepository;

    @GetMapping
    public Page<Invoice> getInvoices(@Parameter Pageable pageable, Authentication authentication) {
        return invoiceRepository.findAllByUserId(((User) authentication.getPrincipal()).getId(), pageable);
    }

    @GetMapping("/{id}")
    public Invoice findInvoice(@PathVariable String id, Authentication authentication) {
        return invoiceRepository.findById(id)
                .filter(invoice -> invoice.getUserId().equals(((User) authentication.getPrincipal()).getId()))
                .orElseThrow(InvoiceNotFoundException::new);
    }

    @PostMapping
    public Invoice addInvoice(@RequestBody Invoice invoice, Authentication authentication) {
        invoice.setKey(StringUtil.generateKey());
        invoice.setUserId(((User) authentication.getPrincipal()).getId());
        invoice.setCreatedAt(new Date(System.currentTimeMillis()));
        return invoiceRepository.save(invoice);
    }

    @PutMapping
    public Invoice updateInvoice(@RequestBody Invoice invoice) {
        invoiceRepository.findById(invoice.getId())
                .orElseThrow(InvoiceNotFoundException::new);
        return invoiceRepository.save(invoice);
    }

    @PutMapping("/status/{id}/{status}")
    public Invoice updateInvoiceStatus(@PathVariable String id, @PathVariable InvoiceStatus status) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(InvoiceNotFoundException::new);
        invoice.setStatus(status);
        return invoiceRepository.save(invoice);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable String id) {
        invoiceRepository.deleteById(id);
    }
}
