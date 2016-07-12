package com.github.nginate.zookeeper;

public enum ConnectionState {
    DISCONNECTED,
    CONNECTING,
    CONNECTED,
    CONNECTED_READ_ONLY,
    CLOSING,
    CLOSED,
    SESSION_EXPIRED,
    AUTHENTICATION_FAILED
}
