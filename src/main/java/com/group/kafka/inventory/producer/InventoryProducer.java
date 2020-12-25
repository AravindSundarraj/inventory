package com.group.kafka.inventory.producer;

import com.group.kafka.inventory.callback.InventoryCallback;
import com.group.kafka.inventory.domain.PosInvoice;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.Metadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
@Slf4j
public class InventoryProducer {

    private static final String BOOTSTRAP_SERVER = "localhost:9095,localhost:9096,localhost:9097";

    public KafkaProducer<String, PosInvoice> producerConfig() {
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        // it's simple string passed to server , purpose of client ID to track the source of the message.
        prop.put(ProducerConfig.CLIENT_ID_CONFIG, "Producer-INVENTORY-2");
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        prop.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        prop.put(ProducerConfig.ACKS_CONFIG, "all");
        prop.put(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
        prop.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        prop.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        // prop.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:9081");

        prop.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        prop.put(ProducerConfig.LINGER_MS_CONFIG, "20");
        prop.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32 * 1024));

        KafkaProducer<String, PosInvoice> kafkaProducer = new KafkaProducer(prop);

        log.info("Kafka-Producer configuration : {} ", kafkaProducer);
        return kafkaProducer;

    }

    public void processInventory(PosInvoice posInvoice) {
        KafkaProducer<String, PosInvoice> KafkaProducer = producerConfig();
        ProducerRecord producerRecord = new ProducerRecord("TOPIC_INVENTORY", posInvoice.getInvoiceNumber(), posInvoice);

        /*
        The callbacks execute in the producer’s main thread. This guarantees that when we send two messages to the same
        partition one after another, their callbacks will be executed in the same order that we sent them. But it also means
        that the callback should be reasonably fast, to avoid delaying the producer and preventing other messages from being sent.
         If you want to perform a
        blocking operation in the callback, it is recommended to use another thread and perform the operation concurrently.
         */
        Future<Metadata> future = KafkaProducer.send(producerRecord, new InventoryCallback());
        try {
            log.info("Record Meta {} ", future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
