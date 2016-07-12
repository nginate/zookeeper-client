package com.github.nginate.zookeeper.protocol;

import com.github.nginate.zookeeper.network.serialization.Type;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.INT32;
import static com.github.nginate.zookeeper.network.serialization.TypeName.INT64;

@Data
public class ResponseHeader {

    @Type(value = INT32, order = 1)
    private Integer xid;

    @Type(value = INT64, order = 2)
    private Long zxid;

    @Type(value = INT32, order = 3)
    private Integer type;
}
