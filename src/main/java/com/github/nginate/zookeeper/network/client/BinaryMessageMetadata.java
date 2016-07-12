package com.github.nginate.zookeeper.network.client;

import lombok.Value;

@Value
public class BinaryMessageMetadata {
    private final Class<?> responseType;
}
