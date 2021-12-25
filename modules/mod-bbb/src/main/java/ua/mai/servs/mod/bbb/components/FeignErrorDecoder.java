package ua.mai.servs.mod.bbb.components;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("Method Key: {};\n Status Code: {};\n", methodKey, response.status());
        return new ResponseStatusException(HttpStatus.valueOf(response.status()), response.reason());
    }

}
