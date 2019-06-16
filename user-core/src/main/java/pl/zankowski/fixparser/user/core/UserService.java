package pl.zankowski.fixparser.user.core;

import pl.zankowski.fixparser.user.api.UserDetailsTO;
import pl.zankowski.fixparser.user.api.UserRegistrationTO;

public interface UserService {

    UserDetailsTO register(final UserRegistrationTO userRegistration);

}
