package com.zeneo.invoice.dao;

import lombok.Data;

@Data
public class InvoiceItem {

    private String itemName;
    private Integer qty;
    private float price;
    private float total;

}
