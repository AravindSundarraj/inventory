package com.group.kafka.inventory.domain.json;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class PosInvoiceJson {

    private String invoiceNumber;
    private java.lang.Long createdTime;
    private java.lang.Long customerCardNo;
    private java.lang.Double totalAmount;
    private java.lang.Integer numberOfItems;
    private java.lang.String paymentMethods;
    private java.lang.Double taxableAmount;
    private java.lang.Double CGST;
    private java.lang.Integer SGST;
    private java.lang.String cess;
    private java.lang.String storeID;
    private java.lang.String posID;
    private java.lang.Double cashierID;
    private java.lang.Integer customerType;
    private java.lang.String deliveryType;
    private com.group.kafka.inventory.domain.json.DeliveryAddressJson deliveryAddress;
    private java.util.List<com.group.kafka.inventory.domain.json.LineItemJson> invoiceLineItems;
}
