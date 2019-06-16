package pl.zankowski.fixparser.user.api;

import org.immutables.value.Value;

@Value.Immutable
public interface PasswordResetTO {

    String getKey();

    String getPassword();

}
