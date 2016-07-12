package com.github.nginate.zookeeper.protocol;

import com.github.nginate.zookeeper.network.serialization.Type;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.INT32;

@Data
@Builder
public class RequestHeader {

    @Type(value = INT32, order = 1)
    private Integer xid;

    @Type(value = INT32, order = 2)
    private Integer type;
}
