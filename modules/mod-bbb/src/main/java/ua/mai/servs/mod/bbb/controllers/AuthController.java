package ua.mai.servs.mod.bbb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.mai.servs.mod.bbb.payloads.Authentication;
import ua.mai.servs.mod.bbb.security.services.BasicTokenService;

@RestController
public class AuthController {
    BasicTokenService basicTokenService;

    @Autowired
    public AuthController(BasicTokenService basicTokenService) {
        this.basicTokenService = basicTokenService;
    }

    @PostMapping(value = "${servs.modules.bbb.auth-jwt.endpoint}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Authentication> authentication(@RequestHeader(value = "Authorization") String authorizationHeader,
                                                         @RequestParam(required = false, name = "grant_type") String grantType) {
        return ResponseEntity.ok(basicTokenService.getUserPayload(authorizationHeader));
    }
}
