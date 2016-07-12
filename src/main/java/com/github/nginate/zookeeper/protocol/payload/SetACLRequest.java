package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.ApiKey;
import com.github.nginate.zookeeper.network.serialization.Type;
import com.github.nginate.zookeeper.protocol.OpCodes;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.*;

@Data
@Builder
@ApiKey(OpCodes.SET_ACL)
public class SetACLRequest {

    @Type(value = STRING, order = 1)
    private String path;

    @Type(value = WRAPPER, order = 2)
    private ACL[] acl;

    @Type(value = INT32, order = 3)
    private Integer version;
}
