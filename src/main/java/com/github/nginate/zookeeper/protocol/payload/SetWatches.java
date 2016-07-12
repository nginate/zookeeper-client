package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.ApiKey;
import com.github.nginate.zookeeper.network.serialization.Type;
import com.github.nginate.zookeeper.protocol.OpCodes;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.INT64;
import static com.github.nginate.zookeeper.network.serialization.TypeName.STRING;

@Data
@Builder
@ApiKey(OpCodes.SET_WATCHES)
public class SetWatches {

    @Type(value = INT64, order = 1)
    private Long relativeZxid;

    @Type(value = STRING, order = 2)
    private String[] dataWatches;

    @Type(value = STRING, order = 3)
    private String[] existWatches;

    @Type(value = STRING, order = 4)
    private String[] childWatches;
}
