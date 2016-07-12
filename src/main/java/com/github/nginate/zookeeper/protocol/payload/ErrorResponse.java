package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.ApiKey;
import com.github.nginate.zookeeper.network.serialization.Type;
import com.github.nginate.zookeeper.protocol.OpCodes;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.INT32;

@Data
@ApiKey(OpCodes.ERROR)
public class ErrorResponse {

    @Type(INT32)
    private Integer errorCode;
}
