package com.zeneo.invoice.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document
public class Invoice {

    @Id
    private String id;
    private String key;
    private InvoiceFrom from;
    private InvoiceTo to;
    private InvoiceTerms terms;
    private String description;
    private InvoiceStatus status;
    private String userId;
    private Date createdAt;
    private List<InvoiceItem> invoiceItems;
}
