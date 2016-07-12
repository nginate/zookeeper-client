package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.ApiKey;
import com.github.nginate.zookeeper.network.serialization.Type;
import com.github.nginate.zookeeper.protocol.OpCodes;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.*;

@Data
@Builder
@ApiKey(OpCodes.CREATE)
public class CreateRequest {

    @Type(value = STRING, order = 1)
    private String path;

    @Type(value = BYTES, order = 2)
    private byte[] data;

    @Type(value = WRAPPER, order = 3)
    private ACL[] acl;

    @Type(value = INT32, order = 4)
    private Integer flags;
}
