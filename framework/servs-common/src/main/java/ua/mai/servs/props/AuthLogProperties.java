package ua.mai.servs.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/*
Logging service authentication in debug mode (RequestResponseLoggingFilter)
 */
@ConfigurationProperties(prefix = "servs.auth-log")
@Data
public class AuthLogProperties {
    private boolean active;
    private boolean payload;
    // The part of the Uri that uniquely identifies the authentication request.
    private String authUriPart;
}
