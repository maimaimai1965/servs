package ua.mai.servs.mod.aaa.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ua.mai.servs.common.ClientConfig;
import ua.mai.servs.mod.aaa.models.Method001ServB11Resource;
import ua.mai.servs.mod.aaa.payloads.Method001ServB11Request;

import javax.validation.Valid;

@FeignClient(name = "ServB11", url = "${servs.modules.bbb.url}", configuration = ClientConfig.class)
public interface ServB11Client {

    @PostMapping(value ="${servs.modules.bbb.services.serv-b11.endpoints.operations.method001-serv-b11}",
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public Method001ServB11Resource method001ServB11(@Valid @RequestBody Method001ServB11Request method001ServB11Request,
                                                     @RequestHeader(name = "Desc") String desc);

}
