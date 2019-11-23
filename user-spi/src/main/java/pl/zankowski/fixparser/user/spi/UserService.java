package pl.zankowski.fixparser.user.spi;

import pl.zankowski.fixparser.user.api.AccountTO;

import java.util.Optional;

public interface UserService {

    Optional<AccountTO> findAccountByEmail(String email);

}
