package ua.mai.servs.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/*
Logging client authentication in debug mode (RequestResponseLoggingFilter)
 */
@ConfigurationProperties(prefix = "servs.auth-client-log")
@Data
public class AuthClientLogProperties {
    private boolean active;
    private boolean payload;
    // The part of the Uri that uniquely identifies the authentication request.
    private String authUriPart;
}
