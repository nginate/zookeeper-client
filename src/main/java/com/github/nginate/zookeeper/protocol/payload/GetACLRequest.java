package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.ApiKey;
import com.github.nginate.zookeeper.network.serialization.Type;
import com.github.nginate.zookeeper.protocol.OpCodes;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.STRING;

@Data
@Builder
@ApiKey(OpCodes.GET_ACL)
public class GetACLRequest {

    @Type(value = STRING, order = 1)
    private String path;
}
