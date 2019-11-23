package pl.zankowski.fixparser.user.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zankowski.fixparser.common.RandomUtil;
import pl.zankowski.fixparser.mail.MailService;
import pl.zankowski.fixparser.user.api.AccountTO;
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
import java.util.Optional;

@Service
@Transactional
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultUserDetailsService(final UserRepository userRepository, final UserMapper userMapper,
            final PasswordEncoder passwordEncoder, final MailService mailService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
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
        mailService.sendActivationEmail(userMapper.mapMail(savedUser));
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

        mailService.sendResetPasswordEmail(userMapper.mapMail(user));
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

    @Override
    public Optional<AccountTO> findAccountByEmail(final String email) {
        return userRepository.findOneByEmail(email)
                .map(userMapper::mapAccount);
    }

}
