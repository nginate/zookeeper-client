package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.Type;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.INT32;
import static com.github.nginate.zookeeper.network.serialization.TypeName.STRING;

@Data
@Builder
public class WatcherEvent {

    @Type(value = INT32, order = 1)
    private Integer type;

    @Type(value = INT32, order = 2)
    private Integer state;

    @Type(value = STRING, order = 3)
    private Integer path;
}
