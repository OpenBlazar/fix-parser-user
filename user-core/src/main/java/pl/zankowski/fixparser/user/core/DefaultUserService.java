package pl.zankowski.fixparser.user.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.zankowski.fixparser.user.api.UserAlreadyExistsException;
import pl.zankowski.fixparser.user.api.UserDetailsTO;
import pl.zankowski.fixparser.user.api.UserRegistrationTO;
import pl.zankowski.fixparser.user.core.entity.UserEntity;

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
    public UserDetailsTO register(final UserRegistrationTO userRegistration) {
        return (UserDetailsTO) userRepository.findOneByUsername(userRegistration.getUsername())
                .map(user -> {
                    throw new UserAlreadyExistsException("Login already in use.");
                }).orElseGet(() -> userRepository.findOneByEmail(userRegistration.getEmail()).map(user -> {
                    throw new UserAlreadyExistsException("Username already in use.");
                }).orElseGet(() -> {
                    final UserEntity user = userMapper.map(userRegistration);
                    final UserEntity savedUser = userRepository.save(user);
                    // TODO Sent activation email
                    return userMapper.map(savedUser);
                }));
    }

}
