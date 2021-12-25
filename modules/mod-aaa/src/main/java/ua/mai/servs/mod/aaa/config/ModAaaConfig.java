package ua.mai.servs.mod.aaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.mai.servs.clients.bbb.ServB11Client;
import ua.mai.servs.clients.bbb.ServB11Service;
import ua.mai.servs.config.ClientConfig;
import ua.mai.servs.logging.FeignClientRequestResponseLogger;
import ua.mai.servs.components.CustomResponseEntityExceptionHandler;
import ua.mai.servs.config.RequestResponseLoggingFilterConfig;
import ua.mai.servs.clients.bbb.ServB11Props;
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
        RequestResponseLoggingFilterConfig.class,
        ClientConfig.class

})
@EnableConfigurationProperties({
      ModAaaProps.class,
      ServB11Props.class})
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
    public FeignClientRequestResponseLogger customFeignRequestLogging() {
        return new FeignClientRequestResponseLogger();
    }

    @Bean
    public CustomResponseEntityExceptionHandler restExceptionHandler() {
        return new CustomResponseEntityExceptionHandler();
    }

}
