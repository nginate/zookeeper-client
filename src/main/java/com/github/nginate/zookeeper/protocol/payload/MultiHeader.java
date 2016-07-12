package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.ApiKey;
import com.github.nginate.zookeeper.network.serialization.Type;
import com.github.nginate.zookeeper.protocol.OpCodes;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.BOOLEAN;
import static com.github.nginate.zookeeper.network.serialization.TypeName.INT32;

@Data
@Builder
@ApiKey(OpCodes.MULTI)
public class MultiHeader {

    @Type(value = INT32, order = 1)
    private Integer type;

    @Type(value = BOOLEAN, order = 2)
    private Boolean done;

    @Type(value = INT32, order = 3)
    private Integer errorCode;
}
