package com.zeneo.invoice.dao;

import lombok.Data;

import java.util.Date;

@Data
public class InvoiceTerms {

    private Date invoiceDate;
    private Date paymentDue;

}
