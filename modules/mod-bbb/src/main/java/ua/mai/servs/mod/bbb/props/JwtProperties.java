package ua.mai.servs.mod.bbb.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ConfigurationProperties(prefix = "servs.modules.bbb.jwt")
@Data
public class JwtProperties {
    private String secret;
    private String type;
    private String issuer;
    private String audience;
    private String tokenType;
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration expiresIn;
    private String scope;
    private String tenant;
}
