package com.zeneo.invoice.service;

import com.zeneo.invoice.StringUtil;
import com.zeneo.invoice.dao.Invoice;
import com.zeneo.invoice.dao.InvoiceStatus;
import com.zeneo.invoice.dao.User;
import com.zeneo.invoice.exception.InvoiceNotFoundException;
import com.zeneo.invoice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private MongoOperations mongoOperations;

    public Page<Invoice> getInvoices(InvoiceStatus status,
                                     String sortBy,
                                     String order,
                                     int page,
                                     int size,
                                     String userId) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (order.equals("desc")) {
            direction = Sort.Direction.DESC;
        }
        if (status == null) {
            return invoiceRepository.findAllByUserId(userId, PageRequest.of(page, size, Sort.by(direction, sortBy)));
        }
        return invoiceRepository.findAllByUserIdAndStatus(userId, status, PageRequest.of(page, size, Sort.by(direction, sortBy)));
    }

    public Invoice getInvoice(String id, String userId) {
        return invoiceRepository.findById(id)
                .filter(invoice -> invoice.getUserId().equals(userId))
                .orElseThrow(InvoiceNotFoundException::new);
    }

    public Invoice saveInvoice(Invoice invoice, String userId) {
        invoice.setKey(StringUtil.generateKey());
        invoice.setUserId(userId);
        invoice.setCreatedAt(new Date(System.currentTimeMillis()));
        return invoiceRepository.save(invoice);
    }

    public Invoice updateInvoice(Invoice invoice, String userId) {
        invoiceRepository.findById(invoice.getId())
                .orElseThrow(InvoiceNotFoundException::new);
        if (!invoice.getUserId().equals(userId)) {
            throw new AuthenticationServiceException("This invoice cannot be updated by this user");
        }
        return invoiceRepository.save(invoice);
    }

    public Invoice updateInvoiceStatus(String id, InvoiceStatus status, String userId) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(InvoiceNotFoundException::new);
        if (!invoice.getUserId().equals(userId)) {
            throw new AuthenticationServiceException("This invoice cannot be updated by this user");
        }
        invoice.setStatus(status);
        return invoiceRepository.save(invoice);
    }

    public void deleteInvoice(String id, String userId) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(InvoiceNotFoundException::new);
        if (!invoice.getUserId().equals(userId)) {
            throw new AuthenticationServiceException("This invoice cannot be updated by this user");
        }
        invoiceRepository.delete(invoice);
    }

}
