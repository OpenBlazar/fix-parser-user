package pl.zankowski.fixparser.user.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;
import pl.zankowski.fixparser.core.api.ImmutableId;

@Value.Immutable
@JsonSerialize(as = ImmutableUserDetailsTO.class)
@JsonDeserialize(as = ImmutableUserDetailsTO.class)
public interface UserDetailsTO {

    ImmutableId getId();

    String getUsername();

    String getEmail();

}
