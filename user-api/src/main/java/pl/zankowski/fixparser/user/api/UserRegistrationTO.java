package pl.zankowski.fixparser.user.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableUserRegistrationTO.class)
@JsonDeserialize(builder = ImmutableUserRegistrationTO.Builder.class)
public interface UserRegistrationTO {

    String getUsername();

    String getEmail();

    String getPassword();

}
