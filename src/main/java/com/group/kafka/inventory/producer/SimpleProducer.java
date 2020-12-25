package com.group.kafka.inventory.producer;



import org.apache.kafka.clients.Metadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SimpleProducer {

    private static final String BOOTSTRAP_SERVER = "localhost:9092,localhost:9093,localhost:9094";

    public KafkaProducer producerConfig(){
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG , BOOTSTRAP_SERVER);
        // it's simple string passed to server , purpose of client ID to track the source of the message.
        prop.put(ProducerConfig.CLIENT_ID_CONFIG , "Producer-ID-1");
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG , IntegerSerializer.class.getName());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG , StringSerializer.class.getName());
        // IDEMPOTENT make sure message is send exactly once and order is maintained.
        prop.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG , "true");
        prop.put(ProducerConfig.ACKS_CONFIG , "all");
        prop.put(ProducerConfig.RETRIES_CONFIG , Integer.toString(Integer.MAX_VALUE));
        // number of parallel batch is send to kafka broker.
        prop.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION , "5");
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG , BOOTSTRAP_SERVER);

        prop.put(ProducerConfig.COMPRESSION_TYPE_CONFIG , "snappy");
        prop.put(ProducerConfig.LINGER_MS_CONFIG , "20");
        prop.put(ProducerConfig.BATCH_SIZE_CONFIG , Integer.toString(32*1024));

        KafkaProducer kafkaProducer = new KafkaProducer(prop);
        return kafkaProducer;

    }

    public void process() throws ExecutionException, InterruptedException {
        KafkaProducer KafkaProducer =  producerConfig();
        for(int i =0; i < 1000; i++){
            // producerRecord contains : 1. topic name 2. key 3. value
            ProducerRecord producerRecord = new ProducerRecord("TEST-TOPIC" ,i,"MESSAGE_NUMBER_"+i);
            // Send methods comes in 3 flavours 1. Synchronous 2. Asynchronous 3. callback

           Future<Metadata> f = KafkaProducer.send(producerRecord);
           System.out.println("The Meta-Data Info: " +f.get());


        }

    }

 /*   public static void main(String args[]) throws ExecutionException, InterruptedException {
        System.out.println("Kafka simple producer start...");
        SimpleProducer simpleProducer = new SimpleProducer();
        simpleProducer.process();
        System.out.println("Kafka simple producer end...");
    }*/
}

