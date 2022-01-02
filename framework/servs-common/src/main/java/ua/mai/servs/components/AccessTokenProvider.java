package ua.mai.servs.components;

import lombok.extern.slf4j.Slf4j;
import ua.mai.servs.models.AuthenticationResource;
import ua.mai.servs.services.AuthService;
//import ua.telesens.o320.trt.integration.bss.props.MiddlewareProps;

import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AccessTokenProvider {

    private String token = null;
    private long expireTime;

    private final AuthService authService;
//    private final MiddlewareProps middlewareProps;

    public AccessTokenProvider(AuthService authService
//          , MiddlewareProps middlewareProps
    ) {
        this.authService = authService;
//        this.middlewareProps = middlewareProps;
    }

    public String getToken() {
        if (token == null || System.currentTimeMillis() > expireTime) {
            authenticate();
        }
        return token;
    }

    public synchronized void resetToken() {
        this.token = null;
        this.expireTime = 0;
        log.info("ResetToken");
    }

    private synchronized void authenticate() {
        if (token == null || System.currentTimeMillis() > expireTime) {
            AuthenticationResource authenticationResource = authService.authenticate(
                  "Basic " +
                        Base64.getEncoder().withoutPadding().encodeToString(
                            String.format("%s:%s",
                  "testUser",
//                                    middlewareProps.getUser().getLogin(),
                  "testPassword"
//                                    middlewareProps.getUser().getPassword()
                            ).getBytes()
                        ),
                  Map.of("grant_type", "client_credentials"));
            this.token = authenticationResource.getAccessToken();
            expireTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(authenticationResource.getExpiresIn()
                  - 1000
//                    - middlewareProps.getExpireTokenBefore()
            );
            log.info("TokenGet ExpireOn={}", new Date(expireTime));
        }
    }
}
