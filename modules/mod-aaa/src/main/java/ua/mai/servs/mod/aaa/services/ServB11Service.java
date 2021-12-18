package ua.mai.servs.mod.aaa.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.mai.servs.mod.aaa.clients.ServB11Client;
import ua.mai.servs.mod.aaa.models.Method001ServB11Resource;
import ua.mai.servs.mod.aaa.payloads.Method001ServB11Request;

import javax.validation.Valid;

@Service
public class ServB11Service {

    private ServB11Client servB11Client;

    @Autowired
    public ServB11Service(ServB11Client servB11Client) {
        this.servB11Client = servB11Client;
    }

    public Method001ServB11Resource method001ServB11(Method001ServB11Request method001ServB11Request,
                                                                     String desc) {
        return servB11Client.method001ServB11(method001ServB11Request, desc);
    }

}

