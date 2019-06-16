package pl.zankowski.fixparser.user.api;

import pl.zankowski.fixparser.core.api.FixParserBusinessException;

public class UserNotFoundException extends FixParserBusinessException {

    public UserNotFoundException() {
    }

    public UserNotFoundException(final String message) {
        super(message);
    }

    public UserNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
