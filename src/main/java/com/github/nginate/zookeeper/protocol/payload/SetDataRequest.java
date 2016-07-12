package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.ApiKey;
import com.github.nginate.zookeeper.network.serialization.Type;
import com.github.nginate.zookeeper.protocol.OpCodes;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.*;

@Data
@Builder
@ApiKey(OpCodes.SET_DATA)
public class SetDataRequest {

    @Type(value = STRING, order = 1)
    private String path;

    @Type(value = BYTES, order = 2)
    private byte[] data;

    @Type(value = INT32, order = 2)
    private Integer version;
}
