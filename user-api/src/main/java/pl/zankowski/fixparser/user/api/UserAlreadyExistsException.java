package pl.zankowski.fixparser.user.api;

import pl.zankowski.fixparser.core.api.FixParserSystemException;

public class UserAlreadyExistsException extends FixParserSystemException {

    public UserAlreadyExistsException(final String message) {
        super(message);
    }

}
