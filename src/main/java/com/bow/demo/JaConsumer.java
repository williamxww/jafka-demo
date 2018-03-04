package com.bow.demo;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import io.jafka.api.FetchRequest;
import io.jafka.consumer.SimpleConsumer;
import io.jafka.message.MessageAndOffset;
import io.jafka.utils.Utils;

/**
 * @author vv
 * @since 2017/4/29.
 */
public class JaConsumer {
    private SimpleConsumer consumer;

    // Map<topic-partition, offset>
    private Map<String, Long> offsetMap = new ConcurrentHashMap();

    public JaConsumer() {
        consumer = new SimpleConsumer("localhost", 9092, 10_000, 1024 * 1024);
    }

    public long getOffset(String topic, int partition) {
        String topicPartition = topic + "-" + partition;
        Long offset = offsetMap.get(topicPartition);
        if (offset == null) {
            offset = 0L;
        }
        return offset;
    }

    public void updateOffset(String topic, int partition, long offset) {
        offsetMap.put(topic + "-" + partition, offset);
    }

    public void poll(String topic, int partition) throws IOException {
        long offset = getOffset(topic, partition);
        FetchRequest request = new FetchRequest(topic, partition, offset);
        for (MessageAndOffset msg : consumer.fetch(request)) {
            System.out.println(Utils.toString(msg.message.payload(), "UTF-8"));
            updateOffset(topic, partition, msg.offset);
        }
    }

    public static void main(String[] args) throws Exception {
        JaConsumer consumer = new JaConsumer();
        System.out.println("ready to receive message...");
        while (true) {
            consumer.poll(JaProducer.TOPIC, 0);
            TimeUnit.SECONDS.sleep(3);
        }
    }
}
