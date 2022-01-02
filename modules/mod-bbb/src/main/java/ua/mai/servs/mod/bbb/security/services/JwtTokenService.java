package ua.mai.servs.mod.bbb.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.gson.io.GsonDeserializer;
import io.jsonwebtoken.gson.io.GsonSerializer;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ua.mai.servs.mod.bbb.props.JwtProperties;
import ua.mai.servs.mod.bbb.security.services.TokenService;
import ua.mai.servs.models.User;
import ua.mai.servs.models.UserPrincipal;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtTokenService implements TokenService {

    private final JwtProperties jwtProperties;

    @Autowired
    public JwtTokenService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public AbstractMap.SimpleImmutableEntry<String, String> generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("auth",
              List.of("USER", "ADMIN"));
//              appUserRoles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).filter(Objects::nonNull).collect(
//              Collectors.toList()));
        Instant now = Instant.now();
        Instant expirationTime = now.plus(jwtProperties.getExpiresIn().getSeconds(), ChronoUnit.SECONDS);

        Key secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());

        String jti = String.valueOf(UUID.randomUUID());

        String compactTokenString = Jwts.builder()
                .serializeToJsonWith(new GsonSerializer())
                .setClaims(claims)//
//                .setSubject(user.getUsername())
                .setIssuer(jwtProperties.getIssuer())
                .setAudience(jwtProperties.getAudience())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expirationTime))
                .setId(jti)
                .signWith(secretKey, SignatureAlgorithm.HS256)
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

        String username = jwsClaims.getBody().getSubject();
        Integer userId = jwsClaims.getBody().get("id", Integer.class);

        return new UserPrincipal(userId, username);
    }
}
