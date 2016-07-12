package com.github.nginate.zookeeper.protocol;

import com.github.nginate.zookeeper.network.AnswerableMessage;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response implements AnswerableMessage {
    private ResponseHeader header;
    private Object payload;

    @Override
    public Object getCorrelationId() {
        return header.getXid();
    }
}
