package ua.mai.servs.mod.bbb.services;

import ua.mai.servs.models.User;
import ua.mai.servs.models.UserPrincipal;

import java.util.AbstractMap;

public interface TokenService {
    AbstractMap.SimpleImmutableEntry<String, String> generateToken(User user);

    UserPrincipal parseToken(String token);
}
