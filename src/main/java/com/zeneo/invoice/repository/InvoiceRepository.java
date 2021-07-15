package com.zeneo.invoice.repository;

import com.zeneo.invoice.dao.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {
}
