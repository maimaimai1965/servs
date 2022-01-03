package ua.mai.servs.mod.bbb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.mai.servs.components.GlobalResponseEntityExceptionHandler;
import ua.mai.servs.config.RequestResponseLoggingFilterConfig;
import ua.mai.servs.logging.FeignClientRequestResponseLogger;
import ua.mai.servs.mod.bbb.security.config.JwtConfig;
import ua.mai.servs.mod.bbb.security.config.JwtConfigurer;
import ua.mai.servs.props.AuthClientLogProperties;
import ua.mai.servs.props.AuthLogProperties;
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
//        ClientConfig.class,
//        IntegrationClientConfig.class,
      RequestResponseLoggingFilterConfig.class,
      JwtConfig.class,
      JwtConfigurer.class,
})
@EnableConfigurationProperties({
      AuthLogProperties.class,
      AuthClientLogProperties.class
})
@ComponentScan("ua.mai.servs.mod.bbb")
public class ModBbbConfig { //extends WsConfiguration {


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



    @Bean
    public FeignClientRequestResponseLogger customFeignRequestLogging(@Autowired AuthClientLogProperties authClientLogProperties) {
        FeignClientRequestResponseLogger logger = new FeignClientRequestResponseLogger();
        logger.setRequestMessagePrefix("  REQ_IN : ");
        logger.setIncludeRequestPayload(true);
        logger.setMaxRequestPayloadLength(10000);

        logger.setResponseMessagePrefix("  RESP_OUT : ");
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
