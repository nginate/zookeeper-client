package com.github.nginate.zookeeper;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ZookeeperClientConfig {

    private static final int DEFAULT_PORT = 2181;
    private static final int DEFAULT_SESSION_TIMEOUT = 30000;
    private static final int DEFAULT_SPIN_DELAY = 1000;
    private static final int DEFAULT_RETRIES = 0;

    private String host;
    private Integer port;
    private Integer sessionId;
    private Integer sessionPassword;
    private Integer sessionTimeout;
    private Integer spinDelay;
    private Integer retries;

    public static ZookeeperClientConfig defaultConfig(String host) {
        return custom(host).build();
    }

    public static ZookeeperClientConfigBuilder custom(String host) {
        return builder()
                .host(host)
                .port(DEFAULT_PORT)
                .sessionTimeout(DEFAULT_SESSION_TIMEOUT)
                .spinDelay(DEFAULT_SPIN_DELAY)
                .retries(DEFAULT_RETRIES);
    }
}
