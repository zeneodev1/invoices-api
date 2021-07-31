package com.zeneo.invoice.repository;

import com.zeneo.invoice.dao.Invoice;
import com.zeneo.invoice.dao.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {

    Page<Invoice> findAllByUserId(String userId, Pageable pageable);
    Page<Invoice> findAllByUserIdAndStatus(String userId, InvoiceStatus status, Pageable pageable);

}
