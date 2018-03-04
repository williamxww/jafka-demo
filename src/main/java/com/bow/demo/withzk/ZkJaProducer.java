package com.bow.demo.withzk;

import io.jafka.producer.Producer;
import io.jafka.producer.ProducerConfig;
import io.jafka.producer.StringProducerData;
import io.jafka.producer.serializer.StringEncoder;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wwxiang
 * @since 2018/3/4.
 */
public class ZkJaProducer {

    public void start(){
        final Properties props = new Properties();
        props.setProperty("zk.connect", "localhost:2181");
        props.setProperty("serializer.class", StringEncoder.class.getName());
        ProducerConfig producerConfig = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(producerConfig);
        //send some message
        final AtomicInteger messageIndex = new AtomicInteger(0);
        while (messageIndex.get() < 100) {
            StringProducerData data = new StringProducerData("demo");
            data.setKey("0");
            data.add("" + messageIndex.incrementAndGet());
            producer.send(data);
        }
    }


    public static void main(String[] args) {

    }
}
