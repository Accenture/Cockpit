package cockpit.jiragateway.exceptions;

public class JiraException extends Exception {

    public JiraException(String message) {
        super(message);
    }

    public JiraException(String message, Throwable t) {
        super(message, t);
    }

}