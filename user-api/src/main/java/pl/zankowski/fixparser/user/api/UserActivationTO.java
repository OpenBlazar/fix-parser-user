package pl.zankowski.fixparser.user.api;

import org.immutables.value.Value;

@Value.Immutable
public interface UserActivationTO {

    String getActivationKey();

}
