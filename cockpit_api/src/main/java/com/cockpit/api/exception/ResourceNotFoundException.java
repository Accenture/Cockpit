package com.cockpit.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceNotFoundException extends Exception {

	private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    public ResourceNotFoundException(String message) {
        logger.error(message);
    }
}
