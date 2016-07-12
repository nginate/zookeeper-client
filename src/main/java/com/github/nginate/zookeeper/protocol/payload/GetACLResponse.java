package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.ApiKey;
import com.github.nginate.zookeeper.network.serialization.Type;
import com.github.nginate.zookeeper.protocol.OpCodes;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.WRAPPER;

@Data
@Builder
@ApiKey(OpCodes.GET_ACL)
public class GetACLResponse {

    @Type(value = WRAPPER, order = 1)
    private ACL[] acl;

    @Type(value = WRAPPER, order = 2)
    private Stat stat;
}
