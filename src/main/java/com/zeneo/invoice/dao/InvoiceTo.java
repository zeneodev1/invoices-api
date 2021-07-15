package com.zeneo.invoice.dao;

import lombok.Data;

@Data
public class InvoiceTo {

    private String clientName;
    private String clientEmail;
    private String streetAddress;
    private String city;
    private String postCode;
    private String country;

}
