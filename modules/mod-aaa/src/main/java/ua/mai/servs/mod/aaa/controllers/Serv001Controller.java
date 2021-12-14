package ua.mai.servs.mod.aaa.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.mai.servs.mod.aaa.models.Method001Serv001Resource;
import ua.mai.servs.mod.aaa.payloads.Method001Serv001Request;
import ua.mai.servs.mod.aaa.services.Serv001Service;

import javax.validation.Valid;

@RestController
@Validated
@Slf4j
public class Serv001Controller {
    private final Serv001Service serv001Service;

    @Autowired
    public Serv001Controller(Serv001Service serv001Service) {
        this.serv001Service = serv001Service;
    }

//    @PostMapping(value = "${servs.modules.aaa.endpoints.operations.method001-serv001}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/servs/api/servs001/v1/method001Serv001", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Method001Serv001Resource> method001Serv001(@Valid @RequestBody Method001Serv001Request method001Serv001Request
//                                                             ,@RequestHeader(name = "Channel") String channelId
    ) {
        log.debug("methodAaa001()");
        return ResponseEntity.ok(serv001Service.method001Serv001(method001Serv001Request));
    }

//    @PostMapping(value = "${middleware.endpoints.operations.gift-act}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<GiftActivationResponse> giftActivation(@Valid @RequestBody GiftActivationRequest giftActivationRequest) {
//        return ResponseEntity.ok(aaaService.giftActivate(giftActivationRequest));
//    }
}
