package com.cockpit.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JiraException extends Exception {

	private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    public JiraException(String message) {
        logger.error(message);
    }
}
