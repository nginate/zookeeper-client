package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.Type;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.*;

@Data
@Builder
public class AuthPacket {

    @Type(value = INT32, order = 1)
    private Integer type;

    @Type(value = STRING, order = 2)
    private String scheme;

    @Type(value = BYTES, order = 3)
    private byte[] auth;
}
