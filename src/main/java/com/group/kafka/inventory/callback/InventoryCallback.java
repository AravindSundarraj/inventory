package com.group.kafka.inventory.callback;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

@Slf4j
public class InventoryCallback implements Callback {
    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        log.info("Callback triggered : {} , Exception {}" ,recordMetadata , e);
    }
}
