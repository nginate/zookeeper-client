package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.Type;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.INT32;
import static com.github.nginate.zookeeper.network.serialization.TypeName.WRAPPER;

@Data
@Builder
public class ACL {

    @Type(value = INT32, order = 1)
    private Integer perms;

    @Type(value = WRAPPER, order = 2)
    private DataId id;
}
