package com.github.nginate.zookeeper.network.exceptions;

public class CommunicationException extends NetworkException {
    private static final long serialVersionUID = -3230905747517984428L;

    public CommunicationException(String message) {
        super(message);
    }

    public CommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
