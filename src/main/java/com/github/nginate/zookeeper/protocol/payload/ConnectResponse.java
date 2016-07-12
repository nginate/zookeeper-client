package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.Type;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.*;

@Data
@Builder
public class ConnectResponse {

    @Type(value = INT32, order = 1)
    private Integer protocolVersion;

    @Type(value = INT32, order = 2)
    private Integer timeOut;

    @Type(value = INT64, order = 3)
    private Long sessionId;

    @Type(value = BYTES, order = 4)
    private byte[] passwd;
}
