package com.group.kafka.inventory.controller;

import com.group.kafka.inventory.domain.PosInvoice;
import com.group.kafka.inventory.domain.json.PosInvoiceJson;
import com.group.kafka.inventory.mapper.InventoryJsonToAvroObject;
import com.group.kafka.inventory.producer.InventoryProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("inventory")
@Slf4j
public class InventoryController {


    @Autowired
    com.group.kafka.inventory.producer.InventoryProducer inventoryProducer;
    @Autowired
    InventoryJsonToAvroObject inventoryJsonToAvroObject;

    @PostMapping
    public String createInventory(@RequestBody PosInvoiceJson posInvoiceJson) {

        System.out.println("posInvoiceJson ===>> " +posInvoiceJson.getDeliveryType());
        log.info("Trigger Rest PosInvoiceJson {} ", posInvoiceJson);
        PosInvoice posInvoice = inventoryJsonToAvroObject.getInvoiceAvro(posInvoiceJson);
        inventoryProducer.processInventory(posInvoice);
        return "Message is processed in Kafka Topic-";

    }
}
