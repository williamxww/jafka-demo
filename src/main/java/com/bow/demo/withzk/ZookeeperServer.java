package com.bow.demo.withzk;

import java.io.IOException;

import com.github.zkclient.ZkServer;

/**
 * @author wwxiang
 * @since 2018/3/4.
 */
public class ZookeeperServer {

    static {
        System.setProperty("zookeeper.preAllocSize", "1024");//1M data log
    }


    public static ZkServer startZkServer(int port) throws IOException {
         String dataPath="zkdata";
         String logPath=dataPath;
        ZkServer zkServer = new ZkServer(dataPath, logPath, port,
                ZkServer.DEFAULT_TICK_TIME, 100);
        zkServer.start();
        return zkServer;
    }

    public static void closeZkServer(ZkServer server) {
        if (server != null) {
            server.shutdown();
        }
    }

    public static void main(String[] args) throws IOException {
        startZkServer(2181);
        System.out.println("Zk started.");
        System.in.read();
    }
}
