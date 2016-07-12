package com.github.nginate.zookeeper.network;

public interface AnswerableMessage<T> {
    T getCorrelationId();
}
