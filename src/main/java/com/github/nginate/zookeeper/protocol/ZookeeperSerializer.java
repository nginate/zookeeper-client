package com.github.nginate.zookeeper.protocol;

import com.github.nginate.zookeeper.network.client.BinaryClientContext;
import com.github.nginate.zookeeper.network.client.BinaryMessageMetadata;
import com.github.nginate.zookeeper.network.exceptions.SerializationException;
import com.github.nginate.zookeeper.network.serialization.BinaryMessageSerializer;
import com.github.nginate.zookeeper.protocol.payload.*;
import com.google.common.collect.ImmutableMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Map;

import static com.github.nginate.zookeeper.network.serialization.SerializationUtils.deserializeObject;
import static com.github.nginate.zookeeper.network.serialization.SerializationUtils.serializeObject;

/**
 * {@inheritDoc}
 */
public class ZookeeperSerializer implements BinaryMessageSerializer {

    private final Map<Integer, Class<?>> responsesMap = ImmutableMap.<Integer, Class<?>>builder()
            .put(OpCodes.CREATE, CreateResponse.class)
            .put(OpCodes.DELETE, DeleteResponse.class)
            .put(OpCodes.ERROR, ErrorResponse.class)
            .put(OpCodes.EXISTS, ExistsResponse.class)
            .put(OpCodes.GET_ACL, GetACLResponse.class)
            .put(OpCodes.GET_CHILDREN2, GetChildren2Response.class)
            .put(OpCodes.GET_DATA, GetDataResponse.class)
            .put(OpCodes.MULTI, MultiHeader.class)
            .build();

    @Override
    public void serialize(ByteBuf buf, Object message) {
        Request request = (Request) message;

        ByteBuf bodyBuffer = Unpooled.buffer();
        if (request.getHeader() != null) {
            serializeObject(bodyBuffer, request.getHeader());
        }
        if (request.getPayload() != null) {
            serializeObject(bodyBuffer, request.getPayload());
        }

        buf.writeInt(bodyBuffer.readableBytes());
        buf.writeBytes(bodyBuffer);
    }

    @Override
    public Object deserialize(ByteBuf buf, BinaryClientContext clientContext) {
        buf.readInt(); // packet size

        ResponseHeader responseHeader = deserializeObject(buf, ResponseHeader.class);
        if (responseHeader.getType() == OpCodes.MULTI) {

        } else {
            Integer correlationId = responseHeader.getXid();
            Class<?> responseClass = clientContext.getMetadata(correlationId)
                    .map(BinaryMessageMetadata::getResponseType)
                    .orElseThrow(() -> new SerializationException("Unexpected response packet"));
            Object body = deserializeObject(buf, responseClass);
        }

        return Response.builder()
                .header(responseHeader)
//                .payload()
                .build();
    }
}
