package com.bow.demo.withzk;

import java.io.IOException;
import java.util.Properties;

import io.jafka.Jafka;

/**
 * @author wwxiang
 * @since 2018/3/4.
 */
public class ZkJaServer {

    private Jafka[] jafkas;

    public void start() {
        final int jafkaCount = 2;
        jafkas = new Jafka[jafkaCount];
        for (int i = 0; i < jafkaCount; i++) {
            Properties serverProperties = new Properties();
            serverProperties.setProperty("enable.zookeeper", "true");
            serverProperties.setProperty("zk.connect", "localhost:2181");
            serverProperties.setProperty("port", String.valueOf(PortUtils.checkAvailablePort(9092)));
            serverProperties.setProperty("brokerid", "" + (100 + i));
            serverProperties.setProperty("num.partitions", "3");
            serverProperties.setProperty("log.dir", "data/jafka"+i);
            serverProperties.setProperty("num.threads",
                    String.valueOf(Math.min(2, Runtime.getRuntime().availableProcessors())));
            Jafka jafka = new Jafka();
            jafka.start(serverProperties, null, null);
            jafkas[i] = jafka;
        }
    }

    public static void main(String[] args) throws IOException {
        ZkJaServer server = new ZkJaServer();
        server.start();
        System.out.println("Jafka started.");
        System.in.read();
    }
}
