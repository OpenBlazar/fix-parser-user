package pl.zankowski.fixparser.user.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.zankowski.fixparser.user.api.AccountTO;
import pl.zankowski.fixparser.user.api.PasswordResetRequestTO;
import pl.zankowski.fixparser.user.api.PasswordResetTO;
import pl.zankowski.fixparser.user.api.UserActivationTO;
import pl.zankowski.fixparser.user.api.UserNotFoundException;
import pl.zankowski.fixparser.user.api.UserRegistrationTO;

import javax.validation.Valid;

@RestController
@RequestMapping
public class UserRestController {

    private final UserDetailsService userDetailsService;

    @Autowired
    public UserRestController(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public ResponseEntity<AccountTO> findUserByEmail(@RequestParam final String email) {
        try {
            final AccountTO account = userDetailsService.findAccountByEmail(email);
            return ResponseEntity.ok(account);
        } catch (final UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody final UserRegistrationTO registration) {
        userDetailsService.register(registration);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/activation")
    public ResponseEntity activateAccount(@Valid @RequestBody final UserActivationTO activation) {
        userDetailsService.activateAccount(activation);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password-reset/init")
    public ResponseEntity initPasswordReset(@Valid @RequestBody final PasswordResetRequestTO request)
            throws UserNotFoundException {
        userDetailsService.initPasswordReset(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password-reset")
    public ResponseEntity resetPassword(@Valid @RequestBody final PasswordResetTO passwordReset)
            throws UserNotFoundException {
        userDetailsService.resetPassword(passwordReset);
        return ResponseEntity.ok().build();
    }

}
