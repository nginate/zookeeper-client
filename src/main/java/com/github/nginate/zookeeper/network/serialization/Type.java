package com.github.nginate.zookeeper.network.serialization;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Type {
    TypeName value();

    int order() default 0;
}
