package com.github.nginate.zookeeper.protocol.payload;

import com.github.nginate.zookeeper.network.serialization.Type;
import lombok.Builder;
import lombok.Data;

import static com.github.nginate.zookeeper.network.serialization.TypeName.INT32;
import static com.github.nginate.zookeeper.network.serialization.TypeName.INT64;

@Data
@Builder
public class Stat {

    @Type(value = INT64, order = 1)
    private Long czxid;

    @Type(value = INT64, order = 2)
    private Long mzxid;

    @Type(value = INT64, order = 3)
    private Long ctime;

    @Type(value = INT64, order = 4)
    private Long mtime;

    @Type(value = INT32, order = 5)
    private Integer version;

    @Type(value = INT32, order = 6)
    private Integer cversion;

    @Type(value = INT32, order = 7)
    private Integer aversion;

    @Type(value = INT64, order = 8)
    private Long ephemeralOwner;

    @Type(value = INT32, order = 9)
    private Integer dataLength;

    @Type(value = INT32, order = 10)
    private Integer numChildren;

    @Type(value = INT64, order = 11)
    private Long pzxid;
}
