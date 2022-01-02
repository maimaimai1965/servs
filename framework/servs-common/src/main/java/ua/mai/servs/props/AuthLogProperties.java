package ua.mai.servs.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "servs.auth-log")
@Data
public class AuthLogProperties {
    private boolean active;
    private boolean payload;
    private String authUriPart;
}
