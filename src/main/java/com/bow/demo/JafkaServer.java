package com.bow.demo;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import io.jafka.Jafka;
import io.jafka.utils.Closer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vv
 * @since 2017/4/29.
 */
public class JafkaServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JafkaServer.class);

    public void start() {
        Jafka jafka = new Jafka();
        int port = 9092;
        try {
            Properties mainProperties = new Properties();
            mainProperties.setProperty("port", "" + port);
            mainProperties.setProperty("brokerid", "0");
            mainProperties.setProperty("log.dir", "./dataLog");
            jafka.start(mainProperties, null, null);
        } catch (Exception e) {
            Closer.closeQuietly(jafka);
            jafka.awaitShutdown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JafkaServer server = new JafkaServer();
        server.start();
        LOGGER.info("Jafka server started.");
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
