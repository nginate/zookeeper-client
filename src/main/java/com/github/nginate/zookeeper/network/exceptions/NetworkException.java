package com.github.nginate.zookeeper.network.exceptions;

public class NetworkException extends RuntimeException {
    private static final long serialVersionUID = -9198274319918051416L;

    public NetworkException(String message) {
        super(message);
    }

    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}