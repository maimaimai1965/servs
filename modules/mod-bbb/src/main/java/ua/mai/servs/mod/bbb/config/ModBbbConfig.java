package ua.mai.servs.mod.bbb.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.mai.servs.components.CustomResponseEntityExceptionHandler;
import ua.mai.servs.config.RequestResponseLoggingFilterConfig;
import ua.mai.servs.logging.FeignClientRequestResponseLogger;
import ua.mai.servs.mod.bbb.props.JwtProperties;
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

@Import(value = {
//        ClientConfig.class,
//        IntegrationClientConfig.class,
      RequestResponseLoggingFilterConfig.class,
      JwtConfig.class,
})
@EnableConfigurationProperties({
      JwtProperties.class,
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
    public FeignClientRequestResponseLogger customFeignRequestLogging() {
        return new FeignClientRequestResponseLogger();
    }

    @Bean
    public CustomResponseEntityExceptionHandler restExceptionHandler() {
        return new CustomResponseEntityExceptionHandler();
    }
}