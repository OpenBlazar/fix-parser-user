package pl.zankowski.fixparser.user.api;

import org.immutables.value.Value;
import pl.zankowski.fixparser.core.api.ImmutableId;

@Value.Immutable
public interface UserDetailsTO {

    ImmutableId getId();

    String getUsername();

    String getEmail();

}
