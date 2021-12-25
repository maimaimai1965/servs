package ua.mai.servs.mod.bbb.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.gson.io.GsonDeserializer;
import io.jsonwebtoken.gson.io.GsonSerializer;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mai.servs.mod.bbb.props.JwtProperties;
import ua.mai.servs.models.User;
import ua.mai.servs.models.UserPrincipal;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtTokenService implements TokenService {

    private final JwtProperties jwtProperties;

    @Autowired
    public JwtTokenService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public AbstractMap.SimpleImmutableEntry<String, String> generateToken(User user) {
        Instant expirationTime = Instant.now().plus(jwtProperties.getExpiresIn().getSeconds(), ChronoUnit.SECONDS);
        Date expirationDate = Date.from(expirationTime);

        Key key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());

        String jti = String.valueOf(UUID.randomUUID());

        String compactTokenString = Jwts.builder()
                .serializeToJsonWith(new GsonSerializer())
                .setIssuer(jwtProperties.getIssuer())
                .setAudience(jwtProperties.getAudience())
                .setSubject(user.getUsername())
                .setExpiration(expirationDate)
                .setId(jti)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return new AbstractMap.SimpleImmutableEntry<>(jti, compactTokenString);
    }

    @Override
    public UserPrincipal parseToken(String token) {
        byte[] secretBytes = jwtProperties.getSecret().getBytes();

        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .deserializeJsonWith(new GsonDeserializer<>())
                .setSigningKey(secretBytes)
                .build()
                .parseClaimsJws(token);

        String username = jwsClaims.getBody()
                .getSubject();
        Integer userId = jwsClaims.getBody()
                .get("id", Integer.class);

        return new UserPrincipal(userId, username);
    }
}
