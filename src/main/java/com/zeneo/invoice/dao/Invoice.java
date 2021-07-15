package com.zeneo.invoice.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Invoice {

    @Id
    private String id;
    private InvoiceFrom from;
    private InvoiceTo to;
    private InvoiceTerms terms;
    private String description;
    private InvoiceStatus status;

}
