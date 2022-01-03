package ua.mai.servs.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mai.servs.clients.AuthBbbClient;
import ua.mai.servs.models.AuthenticationResource;

import java.util.Map;

@Service
public class AuthExternalService {

    private AuthBbbClient authBbbClient;

    @Autowired
    public AuthExternalService(AuthBbbClient authBbbClient) {
        this.authBbbClient = authBbbClient;
    }

    public AuthenticationResource authenticate(String authorizationHeader, Map<String, String> params) {
        return authBbbClient.authentication(authorizationHeader);
//              , params);
    }
}

