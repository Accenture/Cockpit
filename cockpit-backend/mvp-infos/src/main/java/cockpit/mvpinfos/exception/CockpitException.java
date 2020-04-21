package cockpit.mvpinfos.exception;

import lombok.Getter;

@Getter
public class CockpitException extends RuntimeException {

    private final String error;
    private final String errorDescription;

    public CockpitException(String error, String errorDescription, Exception e) {
        super(e);
        this.error = error;
        this.errorDescription = errorDescription;
    }

 }
