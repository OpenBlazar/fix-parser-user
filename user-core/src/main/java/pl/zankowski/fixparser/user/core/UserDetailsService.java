package pl.zankowski.fixparser.user.core;

import pl.zankowski.fixparser.user.api.PasswordResetRequestTO;
import pl.zankowski.fixparser.user.api.PasswordResetTO;
import pl.zankowski.fixparser.user.api.UserActivationTO;
import pl.zankowski.fixparser.user.api.UserDetailsTO;
import pl.zankowski.fixparser.user.api.UserNotFoundException;
import pl.zankowski.fixparser.user.api.UserRegistrationTO;
import pl.zankowski.fixparser.user.spi.UserService;

public interface UserDetailsService extends UserService {

    UserDetailsTO register(final UserRegistrationTO registration);

    UserDetailsTO activateAccount(final UserActivationTO activation);

    void initPasswordReset(final PasswordResetRequestTO request) throws UserNotFoundException;

    void resetPassword(final PasswordResetTO request) throws UserNotFoundException;


}
