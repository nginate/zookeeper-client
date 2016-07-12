package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.Type;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.*;

@Data
@Builder
public class ConnectRequest {

    @Type(value = INT32, order = 1)
    private Integer protocolVersion;

    @Type(value = INT64, order = 2)
    private Long lastZxidSeen;

    @Type(value = INT32, order = 3)
    private Integer timeOut;

    @Type(value = INT64, order = 4)
    private Long sessionId;

    @Type(value = BYTES, order = 5)
    private byte[] passwd;
}
