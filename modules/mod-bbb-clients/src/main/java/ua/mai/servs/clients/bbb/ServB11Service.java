package ua.mai.servs.clients.bbb;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mai.servs.mod.bbb.models.Method001ServB11Resource;
import ua.mai.servs.mod.bbb.payloads.Method001ServB11Request;

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

