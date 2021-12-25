package ua.mai.servs.mod.bbb.config;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.mai.servs.components.CustomResponseEntityExceptionHandler;
import ua.mai.servs.mod.bbb.components.FeignErrorDecoder;
import ua.mai.servs.mod.bbb.controllers.AuthController;
import ua.mai.servs.mod.bbb.props.JwtProperties;
import ua.mai.servs.mod.bbb.services.*;
//import ua.telesens.o320.trt.emulator.bss.clients.ActivationCallbackClient;
//import ua.telesens.o320.trt.emulator.bss.components.FeignErrorDecoder;
//import ua.telesens.o320.trt.emulator.bss.components.RestExceptionHandler;
//import ua.telesens.o320.trt.emulator.bss.controllers.*;
//import ua.telesens.o320.trt.emulator.bss.props.JwtProperties;
//import ua.telesens.o320.trt.emulator.bss.props.MiddlewareProps;
//import ua.telesens.o320.trt.emulator.bss.props.MockProperties;
//import ua.telesens.o320.trt.emulator.bss.services.*;
//import ua.telesens.o320.trt.emulator.bss.services.impl.BasicTokenService;
//import ua.telesens.o320.trt.emulator.bss.services.impl.JwtTokenService;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Import(value = {
        ClientConfiguration.class,
        JwtConfig.class,
        JwtProperties.class,
//        MiddlewareProps.class,
//        RequestLoggingFilterConfig.class,
})
@Configuration
public class EmulatorConfig {

    private final JwtProperties jwtProperties;
//    private final MockProperties mockProperties;
//    private final MiddlewareProps middlewareProps;

    public EmulatorConfig(JwtProperties jwtProperties) {
//          , MockProperties mockProperties, MiddlewareProps middlewareProps) {
        this.jwtProperties = jwtProperties;
//        this.mockProperties = mockProperties;
//        this.middlewareProps = middlewareProps;
    }

    @Bean
    public TokenService jwtTokenService() {
        return new JwtTokenService(jwtProperties);
    }

    @Bean
    public BasicTokenService basicTokenService() {
        return new BasicTokenService((JwtTokenService) jwtTokenService(), jwtProperties);
    }

//    @Bean
//    public ActivationCallbackService activationCallbackService(ActivationCallbackClient activationCallbackClient) {
//        return new ActivationCallbackService(activationCallbackClient);
//    }
//
//    @Bean
//    public ActivationService activationService(ActivationCallbackClient activationCallbackClient) {
//        return new ActivationService(mockProperties, middlewareProps, activationCallbackService(activationCallbackClient));
//    }

//    @Bean
//    public CorpRbtService corpRbtService() {
//        return new CorpRbtService(mockProperties);
//    }
//
//    @Bean
//    public CustomerManagementService customerManagementService() {
//        return new CustomerManagementService(mockProperties);
//    }
//
//    @Bean
//    public ChargingService chargingService() {
//        return new ChargingService(mockProperties, middlewareProps);
//    }


    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public JwtAccessDeniedHandler jwtAccessDeniedHandler() {
        return new JwtAccessDeniedHandler();
    }

    @Bean
    public AuthController authController() {
        return new AuthController(basicTokenService());
    }

//    @Bean
//    public ActivationController activationController(@Autowired ActivationCallbackClient activationCallbackClient) {
//        return new ActivationController(activationService(activationCallbackClient));
//    }

//    @Bean
//    public ChargingController chargingController() {
//        return new ChargingController(chargingService());
//    }

//    @Bean
//    public CorpRbtController corpRbtController() {
//        return new CorpRbtController(corpRbtService());
//    }
//
//    @Bean
//    public CustomerManagementController customerManagementController() {
//        return new CustomerManagementController(customerManagementService());
//    }

    @Bean
    public FeignErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public CustomResponseEntityExceptionHandler restExceptionHandler() {
        return new CustomResponseEntityExceptionHandler();
//        return new RestExceptionHandler(mockProperties);
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM uuuu HH:mm:ss");

                    @Override
                    public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
                        return new JsonPrimitive(formatter.format(localDateTime));
                    }
                }).create();
    }
}
