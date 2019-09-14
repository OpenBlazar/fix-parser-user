package pl.zankowski.fixparser.user.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.zankowski.fixparser.user.api.AccountTO;
import pl.zankowski.fixparser.user.spi.UserService;

@Service
public class RestClientUserService implements UserService {

    private final RestTemplate restTemplate;

    @Autowired
    public RestClientUserService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public AccountTO findAccountByEmail(final String email) {
        final ResponseEntity<AccountTO> response = restTemplate.getForEntity("http://user-service/?email={email}",
                AccountTO.class, email);

        return HttpStatus.OK == response.getStatusCode()
                ? response.getBody()
                : null;
    }

}
