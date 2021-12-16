package ua.mai.servs.mod.bbb.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.mai.servs.mod.bbb.models.Method001ServB11Resource;
import ua.mai.servs.mod.bbb.models.Method002ServB11Resource;
import ua.mai.servs.mod.bbb.payloads.Method001ServB11Request;
import ua.mai.servs.mod.bbb.services.ServB11Service;

import javax.validation.Valid;

@RestController
@Validated
@Slf4j
public class ServB11Controller {
    private final ServB11Service servB11Service;

    @Autowired
    public ServB11Controller(ServB11Service servA01Service) {
        this.servB11Service = servA01Service;
    }

    @PostMapping(value = "${servs.modules.bbb.endpoints.operations.method001-servB11}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Method001ServB11Resource> method002ServB11(@Valid @RequestBody Method001ServB11Request method001ServB11Request
//                                                             ,@RequestHeader(name = "Channel") String channelId
    ) {
        log.debug("POST method001ServB11()");
        return ResponseEntity.ok(servB11Service.method001ServB11(method001ServB11Request));
    }

    @GetMapping(value = "${servs.modules.bbb.endpoints.operations.method002-servB11}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Method002ServB11Resource> method002ServB11(@RequestParam String id) {
        log.debug("GET method002ServB11(): id=" + id);
        return ResponseEntity.ok(servB11Service.method002ServB11(id));
    }

    //    @PostMapping(value = "${middleware.endpoints.operations.gift-act}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<GiftActivationResponse> giftActivation(@Valid @RequestBody GiftActivationRequest giftActivationRequest) {
//        return ResponseEntity.ok(aaaService.giftActivate(giftActivationRequest));
//    }
}
