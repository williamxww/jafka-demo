package com.bow.demo;

import java.util.Properties;

import io.jafka.producer.Producer;
import io.jafka.producer.ProducerConfig;
import io.jafka.producer.StringProducerData;
import io.jafka.producer.serializer.StringEncoder;

/**
 * @author vv
 * @since 2017/4/29.
 */
public class JaProducer {

    public static final String TOPIC = "Demo";

    private Producer<String, String> producer;

    public JaProducer() {
        Properties producerConfig = new Properties();
        producerConfig.setProperty("broker.list", "0:localhost:9092");
        producerConfig.setProperty("serializer.class", StringEncoder.class.getName());
        producer = new Producer<String, String>(new ProducerConfig(producerConfig));
    }

    public void send(String topic, String key, String message) {
        StringProducerData data = new StringProducerData(topic);
        data.setKey(key);
        data.add(message);
        producer.send(data);
    }

    public void close() {
        producer.close();
    }

    public static void main(String[] args) throws Exception {
        JaProducer producer = new JaProducer();
        System.out.println("please press any key to send message ");
        int num = 0;
        while (true) {
            System.in.read();
            producer.send(TOPIC, null, "hello body");
            num++;
            System.out.println(num + " msg send ");
        }
    }
}
