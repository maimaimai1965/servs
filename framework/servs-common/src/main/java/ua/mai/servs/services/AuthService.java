package ua.mai.servs.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mai.servs.clients.AuthClient;
import ua.mai.servs.models.AuthenticationResource;

import java.util.Map;

@Service
public class AuthService {

    private AuthClient authClient;

    @Autowired
    public AuthService(AuthClient authClient) {
        this.authClient = authClient;
    }

    public AuthenticationResource authenticate(String authorizationHeader, Map<String, String> params) {
        return authClient.authentication(authorizationHeader, params);
    }
}

