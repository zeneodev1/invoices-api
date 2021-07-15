package com.zeneo.invoice.dao;

import lombok.Data;

@Data
public class InvoiceFrom {

    private String streetAddress;
    private String city;
    private String postCode;
    private String country;

}
