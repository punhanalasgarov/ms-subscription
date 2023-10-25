package com.ingress.ms.subscription.annotation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Inherited
@Target(TYPE)
@Retention(RUNTIME)
public @interface Log {
}
