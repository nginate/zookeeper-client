package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.ApiKey;
import com.github.nginate.zookeeper.network.serialization.Type;
import com.github.nginate.zookeeper.protocol.OpCodes;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.INT32;
import static com.github.nginate.zookeeper.network.serialization.TypeName.STRING;

@Data
@Builder
@ApiKey(OpCodes.DELETE)
public class DeleteRequest {

    @Type(value = STRING, order = 1)
    private String path;

    @Type(value = INT32, order = 2)
    private Integer version;
}
