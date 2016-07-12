package com.github.nginate.zookeeper.network.client;

import com.github.nginate.zookeeper.network.serialization.BinaryMessageSerializer;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class BinaryTcpClientConfig {
    private final String host;
    private final int port;
    private final int connectionTimeoutMillis;
    private final int socketTimeoutMillis;
    private final int maxMessagesToRead;
    private final BinaryMessageSerializer serializer;

    public static BinaryTcpClientConfig defaultConfig() {
        return custom().build();
    }

    public static BinaryTcpClientConfigBuilder custom() {
        return builder()
                .host("localhost")
                .port(2128)
                .connectionTimeoutMillis(10000)
                .socketTimeoutMillis(10000)
                .maxMessagesToRead(10);
    }
}
