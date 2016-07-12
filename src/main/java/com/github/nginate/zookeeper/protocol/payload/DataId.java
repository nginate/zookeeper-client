package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.Type;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.STRING;

@Data
@Builder
public class DataId {

    @Type(value = STRING, order = 1)
    private String scheme;

    @Type(value = STRING, order = 2)
    private String id;
}
