package com.ingress.ms.subscription.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomLogger {
    private final Logger logger;
    private CustomLogger(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    public static CustomLogger getLogger(Class<?> clazz) {
        return new CustomLogger(clazz);
    }

    public void info(String format, Object... arguments) {
        logger.info(format, arguments);
    }

}
