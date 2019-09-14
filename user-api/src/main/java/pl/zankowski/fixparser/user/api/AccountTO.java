package pl.zankowski.fixparser.user.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@JsonSerialize(as = ImmutableAccountTO.class)
@JsonDeserialize(builder = ImmutableAccountTO.Builder.class)
public interface AccountTO {

    String getEmail();

    String getPassword();

    List<Role> getRoles();

}
