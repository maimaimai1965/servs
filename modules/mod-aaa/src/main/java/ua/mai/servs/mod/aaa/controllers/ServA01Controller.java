package ua.mai.servs.mod.aaa.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.mai.servs.mod.aaa.models.Method001ServA01Resource;
import ua.mai.servs.mod.aaa.models.Method002ServA01Resource;
import ua.mai.servs.mod.aaa.models.Method003ServA01Resource;
import ua.mai.servs.mod.aaa.payloads.Method001ServA01Request;
import ua.mai.servs.mod.aaa.payloads.Method003ServA01Request;
import ua.mai.servs.mod.aaa.services.ServA01Service;

import javax.validation.Valid;

@RestController
@Validated
@Slf4j
public class ServA01Controller {
    private final ServA01Service servA01Service;

    @Autowired
    public ServA01Controller(ServA01Service servA01Service) {
        this.servA01Service = servA01Service;
    }

    /* Вызов метода сервиса ServA01. */
    @PostMapping(value = "${servs.modules.aaa.services.serv-a01.endpoints.operations.method001-serv-a01}",
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Method001ServA01Resource> method001ServA01(@Valid @RequestBody Method001ServA01Request method001ServA01Request
//                                                             ,@RequestHeader(name = "Channel") String channelId
    ) {
        log.debug("method001ServA01() POST");
        return ResponseEntity.ok(servA01Service.method001ServA01(method001ServA01Request));
    }

    /* Вызов метода локального сервиса ServA02. */
    @GetMapping(value = "${servs.modules.aaa.services.serv-a01.endpoints.operations.method001-serv-a01}",
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Method002ServA01Resource> method001ServA01(@RequestParam String id) {
        log.debug("method001ServA01() GET: id=" + id);
        return ResponseEntity.ok(servA01Service.method002ServA01(id));
    }

    /* Вызов метода внешнего сервиса ServB11. */
    @PostMapping(value = "${servs.modules.aaa.services.serv-a01.endpoints.operations.method003-serv-a01}",
          consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Method003ServA01Resource> method003ServA01(@Valid @RequestBody Method003ServA01Request method003ServA01Request
                                                                     //                                                             ,@RequestHeader(name = "Channel") String channelId
    ) {
        log.debug("method003ServA01() POST");
        return ResponseEntity.ok(servA01Service.method003ServA01(method003ServA01Request));
    }

//    @PostMapping(value = "${middleware.endpoints.operations.gift-act}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<GiftActivationResponse> giftActivation(@Valid @RequestBody GiftActivationRequest giftActivationRequest) {
//        return ResponseEntity.ok(aaaService.giftActivate(giftActivationRequest));
//    }
}
