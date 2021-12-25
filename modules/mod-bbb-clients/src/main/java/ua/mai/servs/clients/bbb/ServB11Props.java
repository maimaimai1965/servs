package ua.mai.servs.clients.bbb;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "servs.modules.bbb.services.serv-b11")
@Data
public class ServB11Props {
//    private String url;
    private Endpoints endpoints;
//    private User user;
//    private EventType eventType;
//    private String[] eventTypesNames;
//    private int expireTokenBefore;

//    @Data
//    public static class User {
//        private String login;
//        private String password;
//    }

    @Data
    public static class Endpoints {
        private Operations operations;
//        private List<OperationMethodUri> operationMethodUri;
    }

    @Data
    public static class Operations {
        private String login;
        private String corpmsisdns;
    }

//    @Data
//    public static class OperationMethodUri {
//        private String operation;
//        private String uriTemplate;
//        private String methodName;
//    }

//    @Data
//    public static class EventType {
//        private String activationEvent;
//        private String corpActivationEvent;
//        private String deactivationEvent;
//        private String corpDeactivationEvent;
//        private String giftActivationEvent;
//        private String giftProvisioningEvent;
//        private String msisdnChangeEvent;
//        private String subSuspendEvent;
//        private String subResumeEvent;
//        private String amountChargingEvent;
//        private String amountChargingReservEvent;
//        private String amountChargingConfEvent;
//        private String msisdnValidationEvent;
//        private String bssActivationEvent;
//        private String bssDeactivationEvent;
//        private String siebelInitiatedRbtActivation;
//    }
}
