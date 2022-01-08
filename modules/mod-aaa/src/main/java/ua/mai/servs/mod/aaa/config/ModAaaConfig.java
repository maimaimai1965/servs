package ua.mai.servs.mod.aaa.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.mai.servs.clients.AuthBbbClient;
import ua.mai.servs.components.AccessTokenProvider;
import ua.mai.servs.components.FeignClientInterceptor;
import ua.mai.servs.config.ClientConfig;
import ua.mai.servs.logging.FeignClientRequestResponseLogger;
import ua.mai.servs.components.GlobalResponseEntityExceptionHandler;
import ua.mai.servs.config.RequestResponseLoggingFilterConfig;
import ua.mai.servs.clients.bbb.ServB11Props;
import ua.mai.servs.logging.RequestResponseLoggingFilter;
import ua.mai.servs.props.AuthClientLogProperties;
import ua.mai.servs.props.AuthLogProperties;
import ua.mai.servs.services.AuthExternalService;
//ua.mai.servs.common
//import org.apache.cxf.Bus;
//import org.springframework.security.authentication.AuthenticationManager;
//import ua.telesens.o320.tif.core.ws.WsConfiguration;
//import ua.telesens.o320.trt.be.bss_integration.service_activation.ServiceActivationService;
//import ua.telesens.o320.trt.be.bss_integration.vfua_integration.VFUAIntegrationService;
//import ua.telesens.o320.trt.integration.bss.components.RestExceptionHandler;
//import ua.telesens.o320.trt.integration.bss.controllers.NotificationController;
//import ua.telesens.o320.trt.integration.bss.services.NotificationService;
//import ua.telesens.o320.trt.integration.bss.services.impl.ChargingImpl;
//import javax.xml.ws.Endpoint;

@Configuration
@Import(value = {
//        IntegrationClientConfig.class,
        RequestResponseLoggingFilterConfig.class,
        ClientConfig.class
})
@EnableConfigurationProperties({
      ModAaaProps.class,
      ServB11Props.class,
      AuthLogProperties.class,
      AuthClientLogProperties.class
})
@ComponentScan(basePackages={"ua.mai.servs.mod.aaa",
                             "ua.mai.servs.clients"})
public class ModAaaConfig { //extends WsConfiguration {


//    @Bean
//    public NotificationController notificationController(@Autowired NotificationService notificationService) {
//        return new NotificationController(notificationService);
//    }

//    @Bean
//    public CustomResponseLoggingFilter logResponseFilter() {
//        return new CustomResponseLoggingFilter();
//    }

//    @Bean
//    public DoogiesRequestLogger logResponseFilter() {
//        return new DoogiesRequestLogger();
//    }


//    @Bean
//    public ServB11Service servB11Service(ServB11Client servB11Client) {
//        return new ServB11Service(servB11Client);
//    }


    @Bean
    public RequestInterceptor feignClientInterceptor() {
        return new FeignClientInterceptor();
    }

    @Bean
    public AuthExternalService authService(@Autowired AuthBbbClient authBbbClient) {
        return new AuthExternalService(authBbbClient);
    }

    @Bean
    public AccessTokenProvider accessTokenProvider(@Autowired AuthExternalService authExternalService) {
        return new AccessTokenProvider(authExternalService
              //              , middlewareProps
        );
    }

    @Bean
    public FeignClientRequestResponseLogger customFeignRequestLogging(@Autowired AuthClientLogProperties authClientLogProperties) {
        FeignClientRequestResponseLogger logger = new FeignClientRequestResponseLogger();
        logger.setRequestMessagePrefix("  REQ_OUT -> ");
        logger.setIncludeRequestPayload(true);
        logger.setMaxRequestPayloadLength(10000);

        logger.setResponseMessagePrefix("  RESP_IN <- ");
        logger.setIncludeResponsePayload(true);
        logger.setMaxResponsePayloadLength(10000);

        logger.setAuthLogActive(authClientLogProperties.isActive());
        logger.setAuthLogPayload(authClientLogProperties.isPayload());
        logger.setAuthLogAuthUriPart(authClientLogProperties.getAuthUriPart());

        return logger;
    }

    @Bean
    public GlobalResponseEntityExceptionHandler restExceptionHandler() {
        return new GlobalResponseEntityExceptionHandler();
    }

}
