package pl.zankowski.fixparser.user.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableUserActivationTO.class)
@JsonDeserialize(builder = ImmutableUserActivationTO.Builder.class)
public interface UserActivationTO {

    String getActivationKey();

}
