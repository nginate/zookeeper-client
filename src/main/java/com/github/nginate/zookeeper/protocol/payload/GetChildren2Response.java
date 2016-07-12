
package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.ApiKey;
import com.github.nginate.zookeeper.network.serialization.Type;
import com.github.nginate.zookeeper.protocol.OpCodes;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.STRING;
import static com.github.nginate.zookeeper.network.serialization.TypeName.WRAPPER;

@Data
@Builder
@ApiKey(OpCodes.GET_CHILDREN2)
public class GetChildren2Response {

    @Type(value = STRING, order = 1)
    private String[] children;

    @Type(value = WRAPPER, order = 2)
    private Stat stat;
}
