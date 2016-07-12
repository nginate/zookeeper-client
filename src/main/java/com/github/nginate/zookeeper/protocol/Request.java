package com.github.nginate.zookeeper.protocol;

import com.github.nginate.zookeeper.network.AnswerableMessage;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Request implements AnswerableMessage<Integer> {
    private RequestHeader header;
    private Object payload;

    @Override
    public Integer getCorrelationId() {
        return header.getXid();
    }
}
