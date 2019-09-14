package pl.zankowski.fixparser.user.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutablePasswordResetRequestTO.class)
@JsonDeserialize(builder = ImmutablePasswordResetRequestTO.Builder.class)
public interface PasswordResetRequestTO {

    String getEmail();

}
