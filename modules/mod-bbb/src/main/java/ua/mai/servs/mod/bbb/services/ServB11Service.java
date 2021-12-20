package ua.mai.servs.mod.bbb.services;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import ua.mai.servs.mod.bbb.models.Method001ServB11Resource;
import ua.mai.servs.mod.bbb.models.Method002ServB11Resource;
import ua.mai.servs.mod.bbb.payloads.Method001ServB11Request;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class ServB11Service {

    private ServB12Service servB12;

    @Autowired
    public ServB11Service(ServB12Service servB12) {
        this.servB12 = servB12;
    }

    @PostConstruct
    private void postConstruct() {
//        msisdnPatterns = new ArrayList<>();
//        activationErrorPatterns = new HashMap<>();
//        giftErrorPatterns = new HashMap<>();
//        chargingErrorPatterns = new HashMap<>();
//
//        mockProperties.getProvisioningSettings().forEach(settings -> {
//            msisdnPatterns.add(settings.getPattern());
//            if (!settings.getErrors().isEmpty()) {
//                Map<String, MockProperties.Error> activErrorsByOperation = new HashMap<>();
//                settings.getErrors().forEach(error -> {
//                    if (error.equals(mockProperties.getErrorType().getActMsisdnNotFound().getName())) {
//                        activErrorsByOperation.put(mockProperties.getErrorType().getActMsisdnNotFound().getOperation(),
//                                mockProperties.getErrorType().getActMsisdnNotFound());
//                    } else if (error.equals(mockProperties.getErrorType().getDeactMsisdnNotFound().getName())) {
//                        activErrorsByOperation.put(mockProperties.getErrorType().getDeactMsisdnNotFound().getOperation(),
//                                mockProperties.getErrorType().getDeactMsisdnNotFound());
//                    } else if (error.equals(mockProperties.getErrorType().getNotAvailable().getName())) {
//                        activErrorsByOperation.put(mockProperties.getErrorType().getNotAvailable().getOperation(),
//                                mockProperties.getErrorType().getNotAvailable());
//                    } else if (error.equals(mockProperties.getErrorType().getNotActive().getName())) {
//                        activErrorsByOperation.put(mockProperties.getErrorType().getNotActive().getOperation(),
//                                mockProperties.getErrorType().getNotActive());
//                    } else if (error.equals(mockProperties.getErrorType().getLowBalance().getName())) {
////                        chargingErrorPatterns.put(mockProperties.getErrorType().getLowBalance().getOperation(),
////                                mockProperties.getErrorType().getLowBalance());
//                        chargingErrorPatterns.put(settings.getPattern(), mockProperties.getErrorType().getLowBalance());
//                    } else if (error.equals(mockProperties.getErrorType().getErrorPrepayOrderGiftNotEnoughBalance().getName())) {
//                        giftErrorPatterns.put(settings.getPattern(),
//                                mockProperties.getErrorType().getErrorPrepayOrderGiftNotEnoughBalance());
//                    } else if (error.equals(mockProperties.getErrorType().getErrorPrepayOrderGiftGeneralError().getName())) {
//                        giftErrorPatterns.put(settings.getPattern(),
//                                mockProperties.getErrorType().getErrorPrepayOrderGiftGeneralError());
//                    } else if (error.equals(mockProperties.getErrorType().getActUnknownError().getName())) {
//                        activErrorsByOperation.put(mockProperties.getErrorType().getActUnknownError().getOperation(),
//                                mockProperties.getErrorType().getActUnknownError());
//                    } else if (error.equals(mockProperties.getErrorType().getDeactUnknownError().getName())) {
//                        activErrorsByOperation.put(mockProperties.getErrorType().getDeactUnknownError().getOperation(),
//                                mockProperties.getErrorType().getDeactUnknownError());
//                    } else if (error.equals(mockProperties.getErrorType().getActUnexpectedError().getName())) {
//                        activErrorsByOperation.put(mockProperties.getErrorType().getActUnexpectedError().getOperation(),
//                                mockProperties.getErrorType().getActUnexpectedError());
//                    } else if (error.equals(mockProperties.getErrorType().getDeactUnexpectedError().getName())) {
//                        activErrorsByOperation.put(mockProperties.getErrorType().getDeactUnexpectedError().getOperation(),
//                                mockProperties.getErrorType().getDeactUnexpectedError());
//                    }
//                });
//                activationErrorPatterns.put(settings.getPattern(), activErrorsByOperation);
//            }
//        });
    }

    public Method001ServB11Resource method001ServB11(Method001ServB11Request method001ServB11Request, String desc) {
        log.debug("method001ServB11(): id=" + method001ServB11Request.getId());
        String service = method001ServB11Request.getService();
        if (!StringUtils.isEmpty(service)) {
            desc = "from service " + service + (desc!=null ? "[" + desc + "]" : "");
//            throw new ArrayIndexOutOfBoundsException();
        } else {
            desc = desc!=null ? "[" + desc + "]" : "";
//            throw new ArrayIndexOutOfBoundsException();
        }
        Method001ServB11Resource resource = Method001ServB11Resource.builder()
                .desc(desc)
                .state(method001ServB11Request.getState())
                .build();
//        log.debug("  return: method001Serv001Request.id=" + resource.getId());
        return resource;
    }

    public Method002ServB11Resource method002ServB11(String id) {
        log.debug("method002ServB11(): id=" + id);
        String processedId = servB12.method001ServB12(id);
        Method002ServB11Resource resource = Method002ServB11Resource.builder()
              .processedId(processedId)
              .build();
        return resource;
    }


//    private void deAndActivationCallback(String id, DelayTosOcsIdTuple delay, String
//            eventType, String eventTime, String errorKey, String msisdn) {
//        final String result, tos, ocsId;
//        if (eventType.equals(middlewareProps.getEventType().getActivationEvent()) ||
//                eventType.equals(middlewareProps.getEventType().getCorpActivationEvent())) {
//            result = "activated";
//            tos = delay.getTos();
//            ocsId = delay.getOcsId();
//        } else {
//            result = "deactivated";
//            tos = null;
//            ocsId = null;
//        }
//
//        TimerTask provisioningTask = new TimerTask() {
//            @Override
//            public void run() {
//                Optional<String> optError = Optional.ofNullable(errorKey);
//                MockProperties.Error changinKey = chargingErrorPatterns.get(errorKey);
//
//                if (optError.isPresent() && changinKey == null && activationErrorPatterns.get(errorKey).get(eventType) != null) {
//                    activationCallbackService.activationCallback(new NotificationCallbackRequest(id,
//                            id,
//                            new EventCallbackRequest(
//                                    eventType,
//                                    eventTime,
//                                    CHANNEL_ID,
//                                    id,
//                                    EventDetailsCallbackRequest.builder()
//                                            .tos(tos).ocsId(ocsId)
//                                            .result("error").msisdn(msisdn).serviceCode(CHANNEL_ID)
//                                            .build(), activationErrorPatterns.get(errorKey).get(eventType).getName(),
//                                    activationErrorPatterns.get(errorKey).get(eventType).getMsg()
//                            ), "http://domain/api/rbt/notification"));
//                } else {
//                    activationCallbackService.activationCallback(new NotificationCallbackRequest(id,
//                            id,
//                            new EventCallbackRequest(
//                                    eventType,
//                                    eventTime,
//                                    CHANNEL_ID,
//                                    id,
//                                    EventDetailsCallbackRequest.builder()
//                                            .tos(tos).ocsId(ocsId)
//                                            .result(result).msisdn(msisdn).serviceCode(CHANNEL_ID)
//                                            .build(),
//                                    null, null
//                            ), "http://domain/api/rbt/notification"));
//                }
//            };
//        };
//        delayedCallbackBuild(provisioningTask,  delay.getDelay() + 2000  ); //base waiting + 2000 milsec delay
//    }
//
//    private void delayedCallbackBuild(TimerTask task, Long delay) {
//        Timer timer = new Timer();
//        timer.schedule(task, delay);
//    }
//
//    public GiftActivationResponse giftActivate(GiftActivationRequest giftActivationRequest) {
//        giftErrorPatterns.keySet().stream()
//                .filter(giftActivationRequest.getProduct().getId()::matches)
//                .findFirst().ifPresent(s -> {
//            throw new CustomException(giftErrorPatterns.get(s).getName(),
//                    new Throwable(giftErrorPatterns.get(s).getMsg()));
//        });
//        String giftProvisionId = String.valueOf(System.nanoTime());
//
//        TimerTask provisioningTask = new TimerTask() {
//            @Override
//            public void run() {
//                String id = String.valueOf(UUID.randomUUID());
//                String eventTime = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
//                    activationCallbackService.activationCallback(new NotificationCallbackRequest(
//                            id, id,
//                            new EventCallbackRequest(
//                                    "RBT-GIFT-PROVISIONING",
//                                    eventTime,
//                                    CHANNEL_ID,
//                                    id,
//                                    EventDetailsCallbackRequest.builder()
//                                            .state("completed")
//                                            .giftProvisionId(giftProvisionId)
//                                            .build(),
//                                    null,
//                                    null
//                            ), "http://domain/api/rbt/notification"));
//            };
//        };
//        delayedCallbackBuild(provisioningTask, 500L );
//
//        return GiftActivationResponse.builder().giftProvisionId(giftProvisionId).build();
//    }
}
