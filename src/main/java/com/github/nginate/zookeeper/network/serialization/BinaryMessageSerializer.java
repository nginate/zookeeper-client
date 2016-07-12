package com.github.nginate.zookeeper.network.serialization;

import com.github.nginate.zookeeper.network.client.BinaryClientContext;
import com.github.nginate.zookeeper.network.exceptions.SerializationException;
import io.netty.buffer.ByteBuf;

/**
 * Serializer for binary messages.
 */
public interface BinaryMessageSerializer {
    /**
     * Write message directly to netty byte buffer. Could throw runtime serialization exception
     *
     * @param buf     buffer
     * @param message message object
     * @throws SerializationException if any serialization error occurs
     */
    void serialize(ByteBuf buf, Object message);

    /**
     * Deserialize response from netty byte buffer
     *
     * @param buf           buffer
     * @param clientContext context containing response type and correlation id
     * @return response with type, set within client context
     * @throws SerializationException if any serialization error occurs
     */
    Object deserialize(ByteBuf buf, BinaryClientContext clientContext);
}
