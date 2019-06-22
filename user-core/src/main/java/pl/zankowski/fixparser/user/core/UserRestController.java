package pl.zankowski.fixparser.user.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zankowski.fixparser.user.api.PasswordResetRequestTO;
import pl.zankowski.fixparser.user.api.PasswordResetTO;
import pl.zankowski.fixparser.user.api.UserActivationTO;
import pl.zankowski.fixparser.user.api.UserNotFoundException;
import pl.zankowski.fixparser.user.api.UserRegistrationTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody final UserRegistrationTO registration) {
        userService.register(registration);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/activation")
    public ResponseEntity activateAccount(@Valid @RequestBody final UserActivationTO activation) {
        userService.activateAccount(activation);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password-reset/init")
    public ResponseEntity initPasswordReset(@Valid @RequestBody final PasswordResetRequestTO request)
            throws UserNotFoundException {
        userService.initPasswordReset(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password-reset")
    public ResponseEntity resetPassword(@Valid @RequestBody final PasswordResetTO passwordReset)
            throws UserNotFoundException {
        userService.resetPassword(passwordReset);
        return ResponseEntity.ok().build();
    }

}
