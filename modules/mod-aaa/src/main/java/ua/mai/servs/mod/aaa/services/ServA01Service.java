package ua.mai.servs.mod.aaa.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.mai.servs.mod.aaa.models.Method001ServA01Resource;
import ua.mai.servs.mod.aaa.models.Method002ServA01Resource;
import ua.mai.servs.mod.aaa.payloads.Method001ServA01Request;
import javax.annotation.PostConstruct;

@Slf4j
@Service
public class ServA01Service {

//    @Autowired
//    public AaaService(MockProperties mockProperties, MiddlewareProps middlewareProps, ActivationCallbackService activationCallbackService) {
//        this.mockProperties = mockProperties;
//        this.middlewareProps = middlewareProps;
//        this.activationCallbackService = activationCallbackService;
//    }

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

    public Method001ServA01Resource method001ServA01(Method001ServA01Request method001ServA01Request) {
        log.debug("method001Serv001(): state=" + method001ServA01Request.getState());
        Method001ServA01Resource resource = Method001ServA01Resource.builder()
                .id(java.util.UUID.randomUUID().toString())
                .state(method001ServA01Request.getState())
                .build();
//        log.debug("  return: method001Serv001Request.id=" + resource.getId());
        return resource;
    }

    public Method002ServA01Resource method002ServA01(String id) {
        log.debug("method002Serv001(): id=" + id);
        Method002ServA01Resource resource = Method002ServA01Resource.builder()
              .desc("description of " + id)
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
