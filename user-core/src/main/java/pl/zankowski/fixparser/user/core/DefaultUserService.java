package pl.zankowski.fixparser.user.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.zankowski.fixparser.common.RandomUtil;
import pl.zankowski.fixparser.user.api.PasswordResetRequestTO;
import pl.zankowski.fixparser.user.api.PasswordResetTO;
import pl.zankowski.fixparser.user.api.UserActivationTO;
import pl.zankowski.fixparser.user.api.UserAlreadyExistsException;
import pl.zankowski.fixparser.user.api.UserDetailsTO;
import pl.zankowski.fixparser.user.api.UserNotFoundException;
import pl.zankowski.fixparser.user.api.UserRegistrationTO;
import pl.zankowski.fixparser.user.core.entity.UserEntity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultUserService(final UserRepository userRepository, final UserMapper userMapper,
            final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetailsTO register(final UserRegistrationTO registration) {
        return (UserDetailsTO) userRepository.findOneByUsername(registration.getUsername())
                .map(user -> {
                    throw new UserAlreadyExistsException("Login already in use.");
                }).orElseGet(() -> userRepository.findOneByEmail(registration.getEmail())
                        .map(user -> {
                            throw new UserAlreadyExistsException("Username already in use.");
                        }).orElseGet(() -> registerSupplier(registration)));
    }

    private UserDetailsTO registerSupplier(final UserRegistrationTO registration) {
        final UserEntity savedUser = userRepository.save(userMapper.map(registration));
        // TODO Send activation email
        return userMapper.map(savedUser);
    }

    @Override
    public UserDetailsTO activateAccount(final UserActivationTO activation) {
        return userRepository.findOneByActivationKey(activation.getActivationKey())
                .map(this::activateAccount).get();
    }

    private UserDetailsTO activateAccount(final UserEntity user) {
        return userMapper.map(userRepository.save(UserEntity.builder(user)
                .activated(true)
                .build()));
    }

    @Override
    public void initPasswordReset(final PasswordResetRequestTO request) throws UserNotFoundException {
        final UserEntity user = userRepository.findOneByEmail(request.getEmail())
                .filter(UserEntity::isActivated)
                .map(this::initPasswordReset)
                .orElseThrow(UserNotFoundException::new);

        // TODO send email with password reset
    }

    private UserEntity initPasswordReset(final UserEntity user) {
        return userRepository.save(UserEntity.builder(user)
                .resetKey(RandomUtil.randomUuid())
                .resetDate(Instant.now())
                .build());
    }

    @Override
    public void resetPassword(final PasswordResetTO request) throws UserNotFoundException {
        userRepository.findOneByResetKey(request.getKey())
                .filter(user -> isResetKeyExpired(user.getResetDate()))
                .map(user -> resetPassword(user, request))
                .orElseThrow(UserNotFoundException::new);
    }

    private boolean isResetKeyExpired(final Instant resetDate) {
        return resetDate.isAfter(Instant.now().minus(1, ChronoUnit.DAYS));
    }

    private UserEntity resetPassword(final UserEntity user, final PasswordResetTO passwordReset) {
        return userRepository.save(UserEntity.builder(user)
                .password(passwordEncoder.encode(passwordReset.getPassword()))
                .resetKey(null)
                .resetDate(null)
                .build());
    }

}
