package com.cockpit.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpException extends Exception {

	private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    public HttpException(String message) {
        logger.error(message);
    }
}
