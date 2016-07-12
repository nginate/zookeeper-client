package com.github.nginate.zookeeper.network.exceptions;

public class SerializationException extends NetworkException {
    private static final long serialVersionUID = 150386498975596551L;

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
