package com.bow.demo.withzk;

import io.jafka.consumer.Consumer;
import io.jafka.consumer.ConsumerConfig;
import io.jafka.consumer.ConsumerConnector;
import io.jafka.consumer.MessageStream;
import io.jafka.producer.serializer.StringDecoder;
import io.jafka.producer.serializer.StringEncoder;
import io.jafka.utils.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wwxiang
 * @since 2018/3/4.
 */
public class ZkJaConsumer {


    public void start(){
        Properties props = new Properties();
        props.setProperty("zk.connect", "localhost:2181");
        props.setProperty("serializer.class", StringEncoder.class.getName());
        props.setProperty("groupid", "group1");
        props.setProperty("consumerid", "consumer1");
        ConsumerConfig consumerConfig = new ConsumerConfig(props);
        // 连接zookeeper
        ConsumerConnector connector = Consumer.create(consumerConfig);
        // 每个topic分配2个线程去处理
        int topicCount = 2;
        // Map<topic, 多个queue(与线程数一致)>
        Map<String, List<MessageStream<String>>> map = connector.createMessageStreams(
                ImmutableMap.of("demo", topicCount), new StringDecoder());
        ExecutorService service = Executors.newFixedThreadPool(topicCount);
        List<MessageStream<String>> streams = map.get("demo");
        for (final MessageStream<String> stream : streams) {
            service.submit(new Runnable() {
                public void run() {
                    for (String message : stream) {
                        System.out.println(message);
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        ZkJaConsumer consumer = new ZkJaConsumer();
        consumer.start();
        System.out.println("Consumer started.");
    }
}
