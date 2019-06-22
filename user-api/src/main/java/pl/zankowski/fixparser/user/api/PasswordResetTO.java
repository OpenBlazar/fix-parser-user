package pl.zankowski.fixparser.user.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutablePasswordResetTO.class)
@JsonDeserialize(as = ImmutablePasswordResetTO.class)
public interface PasswordResetTO {

    String getKey();

    String getPassword();

}
