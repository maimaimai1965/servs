package ua.mai.servs.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import ua.mai.servs.config.ClientConfig;
import ua.mai.servs.models.AuthenticationResource;

import java.util.Map;

@FeignClient(name = "auth-jwt", url = "${servs.modules.bbb.url}", configuration = ClientConfig.class)
public interface AuthBbbClient {

    @PostMapping(value = "${servs.modules.bbb.auth-jwt.endpoint}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    AuthenticationResource authentication(@RequestHeader(value = "Authorization") String authorizationHeader);
//                                          @SpringQueryMap Map<String, String> params);
}
