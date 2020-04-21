package cockpit.mvpinfos.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceNotFoundException extends Exception {
    
	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ResourceNotFoundException(String message) {
        logger.error(message);
    }
}
