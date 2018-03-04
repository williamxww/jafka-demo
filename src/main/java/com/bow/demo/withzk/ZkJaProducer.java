package com.bow.demo.withzk;

import io.jafka.producer.Producer;
import io.jafka.producer.ProducerConfig;
import io.jafka.producer.StringProducerData;
import io.jafka.producer.serializer.StringEncoder;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wwxiang
 * @since 2018/3/4.
 */
public class ZkJaProducer {

    public void start() throws IOException {
        final Properties props = new Properties();
        props.setProperty("zk.connect", "localhost:2181");
        props.setProperty("serializer.class", StringEncoder.class.getName());
        ProducerConfig producerConfig = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(producerConfig);
        System.out.println("Producer started.");
        //send some message
        final AtomicInteger index = new AtomicInteger(0);
        while (index.get() < 100) {
            StringProducerData data = new StringProducerData("demo");
            data.setKey("0");
            data.add("" + index.incrementAndGet());
            System.in.read();
            producer.send(data);
            System.out.println("Send 1 msg.");
        }
    }


    public static void main(String[] args) throws IOException {
        ZkJaProducer producer = new ZkJaProducer();
        producer.start();
    }
}
