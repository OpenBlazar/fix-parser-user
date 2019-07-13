package pl.zankowski.fixparser.user.spi;

import pl.zankowski.fixparser.user.api.AccountTO;
import pl.zankowski.fixparser.user.api.UserNotFoundException;

public interface UserService {

    AccountTO findAccountByEmail(String email) throws UserNotFoundException;

}
