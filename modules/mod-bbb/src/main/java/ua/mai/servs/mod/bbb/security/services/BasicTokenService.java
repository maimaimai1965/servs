package ua.mai.servs.mod.bbb.security.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mai.servs.mod.bbb.payloads.Authentication;
import ua.mai.servs.mod.bbb.props.JwtProperties;
import ua.mai.servs.models.User;

import java.util.AbstractMap;
import java.util.Base64;

@Slf4j
@Service
public class BasicTokenService {

    private final JwtProperties jwtProperties;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public BasicTokenService(JwtTokenService jwtTokenService, JwtProperties jwtProperties) {
        this.jwtTokenService = jwtTokenService;
        this.jwtProperties = jwtProperties;
    }

    public User parseToken(String token) {
        String decoded = new String(Base64.getDecoder().decode(token.split(" ")[1].getBytes()));
        return User.builder().username(decoded.split(":")[0]).build();
    }

    public Authentication getUserPayload(String token) {
        User user = parseToken(token);
        // TODO Определение ролей доступных пользователю
        log.debug("Authenticate user '" + user.getUsername() + "'");
        AbstractMap.SimpleImmutableEntry<String, String> accessToken = jwtTokenService.generateToken(user);
        return new Authentication(accessToken.getValue(),
                jwtProperties.getTokenType(), jwtProperties.getExpiresIn().getSeconds(), jwtProperties.getScope(),
                jwtProperties.getTenant(), accessToken.getKey());
    }
}
