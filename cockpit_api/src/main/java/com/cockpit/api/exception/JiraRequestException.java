package com.cockpit.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JiraRequestException extends Exception {

	private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    public JiraRequestException(String message) {
        logger.error(message);
    }
}
