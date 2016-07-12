package com.github.nginate.zookeeper;

import com.github.nginate.zookeeper.network.client.BinaryMessageMetadata;
import com.github.nginate.zookeeper.network.client.BinaryTcpClient;
import com.github.nginate.zookeeper.network.client.BinaryTcpClientConfig;
import com.github.nginate.zookeeper.network.exceptions.CommunicationException;
import com.github.nginate.zookeeper.network.serialization.ApiKey;
import com.github.nginate.zookeeper.protocol.Request;
import com.github.nginate.zookeeper.protocol.RequestHeader;
import com.github.nginate.zookeeper.protocol.Response;
import com.github.nginate.zookeeper.protocol.ZookeeperSerializer;
import com.github.nginate.zookeeper.protocol.payload.ConnectRequest;
import com.github.nginate.zookeeper.protocol.payload.ErrorResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static com.github.nginate.commons.lang.NStrings.format;
import static com.github.nginate.zookeeper.protocol.ProtocolConstants.PROTOCOL_VERSION;
import static com.google.common.base.Preconditions.checkNotNull;

public class ZookeeperClient {

    private final BinaryTcpClient binaryTcpClient;
    private final AtomicInteger correlationIdCounter = new AtomicInteger();
    private final AtomicLong lastZxidSeen = new AtomicLong();
    private final AtomicLong sessionId = new AtomicLong();
    private final ZookeeperClientConfig config;

    public ZookeeperClient(ZookeeperClientConfig config) {
        this.config = config;
        BinaryTcpClientConfig tcpClientConfig = BinaryTcpClientConfig.custom()
                .serializer(new ZookeeperSerializer())
                .host(config.getHost())
                .port(config.getPort())
                .build();
        binaryTcpClient = new BinaryTcpClient(tcpClientConfig);
        sessionId.set(config.getSessionId());
        onConnected();
    }

    private void onConnected() {
        ConnectRequest connectRequest = ConnectRequest.builder()
                .protocolVersion(PROTOCOL_VERSION)
                .lastZxidSeen(lastZxidSeen.get())
                .timeOut(config.getSessionTimeout())
                .sessionId(sessionId.get())
                .build();

        send(null, connectRequest);
    }

    private void send(RequestHeader header, Object payload) {
        Request request = Request.builder()
                .header(header)
                .payload(payload)
                .build();
        binaryTcpClient.send(request);
    }

    private <T> CompletableFuture<T> sendAndReceive(Object payload, Class<T> responseClass)
            throws CommunicationException {
        ApiKey apiKeyAnnotation = payload.getClass().getAnnotation(ApiKey.class);
        checkNotNull(apiKeyAnnotation,
                format("Class {} should be annotated with {}", payload.getClass(), ApiKey.class));

        RequestHeader header = RequestHeader.builder()
                .xid(correlationIdCounter.incrementAndGet())
                .type(apiKeyAnnotation.value())
                .build();
        Request request = Request.builder()
                .header(header)
                .payload(payload)
                .build();

        return sendAndReceive(request, responseClass);
    }

    private <T> CompletableFuture<T> sendAndReceive(Request request, Class<T> responseClass)
            throws CommunicationException {
        return binaryTcpClient.request(request, Response.class, new BinaryMessageMetadata(responseClass))
                .thenApply(response -> {
                    if (response.getPayload() instanceof ErrorResponse) {
                        ErrorResponse errorResponse = (ErrorResponse) response.getPayload();
                        throw new ZookeeperClientException(format("Exception with code {}",
                                errorResponse.getErrorCode()));
                    }
                    return responseClass.cast(response.getPayload());
                });
    }
}
