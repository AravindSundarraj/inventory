package com.group.kafka.inventory.mapper;

import com.group.kafka.inventory.domain.DeliveryAddress;
import com.group.kafka.inventory.domain.LineItem;
import com.group.kafka.inventory.domain.PosInvoice;
import com.group.kafka.inventory.domain.json.LineItemJson;
import com.group.kafka.inventory.domain.json.PosInvoiceJson;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InventoryJsonToAvroObject {


    public PosInvoice getInvoiceAvro(PosInvoiceJson posInvoiceJson) {

        List<LineItem> lineItemList = new ArrayList<>();
        PosInvoice posInvoice = new PosInvoice();
        posInvoice.setCashierID(posInvoiceJson.getCashierID());
        posInvoice.setCESS(posInvoiceJson.getCess());
        posInvoice.setCGST(posInvoiceJson.getCGST());
        posInvoice.setCreatedTime(posInvoiceJson.getCreatedTime());
        posInvoice.setCustomerCardNo(posInvoiceJson.getCustomerCardNo());
        posInvoice.setCustomerType(posInvoiceJson.getCustomerType());
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setAddressLine(posInvoiceJson.getDeliveryAddress().getAddressLine());
        deliveryAddress.setCity(posInvoiceJson.getDeliveryAddress().getAddressLine());
        deliveryAddress.setContactNumber(posInvoiceJson.getDeliveryAddress().getContactNumber());
        deliveryAddress.setPinCode(posInvoiceJson.getDeliveryAddress().getPinCode());
        deliveryAddress.setState(posInvoiceJson.getDeliveryAddress().getState());
        posInvoice.setDeliveryAddress(deliveryAddress);
        for(LineItemJson lineItemJson :posInvoiceJson.getInvoiceLineItems()){
            LineItem lineItem = new LineItem();
            lineItem.setItemCode(lineItemJson.getItemCode());
            lineItem.setItemDescription(lineItemJson.getItemDescription());
            lineItem.setItemPrice(lineItemJson.getItemPrice());
            lineItem.setItemQty(lineItemJson.getItemQty());
            lineItem.setTotalValue(lineItemJson.getTotalValue());

            lineItemList.add(lineItem);
        }
        posInvoice.setInvoiceLineItems(lineItemList);
        posInvoice.setInvoiceNumber(posInvoiceJson.getInvoiceNumber());
        posInvoice.setNumberOfItems(posInvoiceJson.getNumberOfItems());
        posInvoice.setPaymentMethods(posInvoiceJson.getPaymentMethods());
        posInvoice.setPosID(posInvoiceJson.getPosID());
        posInvoice.setSGST(posInvoiceJson.getSGST());
        posInvoice.setStoreID(posInvoiceJson.getStoreID());
        posInvoice.setTaxableAmount(posInvoiceJson.getTaxableAmount());
        posInvoice.setTotalAmount(posInvoiceJson.getTotalAmount());
        return posInvoice;
    }
}
