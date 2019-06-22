package pl.zankowski.fixparser.user.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.zankowski.fixparser.common.RandomUtil;
import pl.zankowski.fixparser.core.api.ImmutableId;
import pl.zankowski.fixparser.mail.api.ImmutableUserMailTO;
import pl.zankowski.fixparser.mail.api.UserMailTO;
import pl.zankowski.fixparser.user.api.ImmutableUserDetailsTO;
import pl.zankowski.fixparser.user.api.UserDetailsTO;
import pl.zankowski.fixparser.user.api.UserRegistrationTO;
import pl.zankowski.fixparser.user.core.entity.UserEntity;

@Component
class UserMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    UserEntity map(final UserRegistrationTO userRegistration) {
        return UserEntity.builder()
                .email(userRegistration.getEmail())
                .username(userRegistration.getUsername())
                .password(passwordEncoder.encode(userRegistration.getPassword()))
                .activationKey(RandomUtil.randomUuid())
                .activated(true)
                .build();
    }

    UserDetailsTO map(final UserEntity userEntity) {
        return ImmutableUserDetailsTO.builder()
                .id(ImmutableId.builder()
                        .id(userEntity.getId())
                        .build())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .build();
    }

    UserMailTO mapMail(final UserEntity user) {
        return ImmutableUserMailTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .activationKey(user.getActivationKey())
                .resetKey(user.getResetKey())
                .build();
    }

}
