package com.github.nginate.zookeeper.network.serialization;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiKey {
    int value();
}
