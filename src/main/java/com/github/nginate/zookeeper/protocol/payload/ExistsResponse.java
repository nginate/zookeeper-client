package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.ApiKey;
import com.github.nginate.zookeeper.network.serialization.Type;
import com.github.nginate.zookeeper.protocol.OpCodes;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.WRAPPER;

@Data
@Builder
@ApiKey(OpCodes.EXISTS)
public class ExistsResponse {

    @Type(value = WRAPPER, order = 1)
    private Stat stat;
}
