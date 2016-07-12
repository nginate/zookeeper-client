package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.ApiKey;
import com.github.nginate.zookeeper.network.serialization.Type;
import com.github.nginate.zookeeper.protocol.OpCodes;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.BOOLEAN;
import static com.github.nginate.zookeeper.network.serialization.TypeName.STRING;

@Data
@Builder
@ApiKey(OpCodes.EXISTS)
public class ExistsRequest {

    @Type(value = STRING, order = 1)
    private String path;

    @Type(value = BOOLEAN, order = 2)
    private Boolean watch;
}
