package ua.mai.servs.mod.aaa.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.mai.servs.common.ClientConfig;
import ua.mai.servs.components.FeignRequestResponseLogger;
import ua.mai.servs.components.RestExceptionHandler;
import ua.mai.servs.config.RequestLoggingFilterConfig;
import ua.mai.servs.mod.aaa.clients.ServB11Props;
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
//        ClientConfig.class,
//        IntegrationClientConfig.class,
        RequestLoggingFilterConfig.class,
        ClientConfig.class

})
@EnableConfigurationProperties({
      ModAaaProps.class,
      ServB11Props.class})
@ComponentScan("ua.mai.servs.mod.aaa")
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

    @Bean
    public FeignRequestResponseLogger customFeignRequestLogging() {
        return new FeignRequestResponseLogger();
    }

    @Bean
    public RestExceptionHandler restExceptionHandler() {
        return new RestExceptionHandler();
    }
}
