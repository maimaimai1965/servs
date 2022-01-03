package ua.mai.servs.mod.bbb.security.config;

import com.google.gson.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.mai.servs.components.GlobalResponseEntityExceptionHandler;
import ua.mai.servs.config.ClientConfig;
import ua.mai.servs.mod.bbb.components.FeignErrorDecoder;
import ua.mai.servs.mod.bbb.controllers.AuthController;
import ua.mai.servs.mod.bbb.props.JwtProperties;
import ua.mai.servs.mod.bbb.security.JwtAccessDeniedHandler;
import ua.mai.servs.mod.bbb.security.JwtAuthEntryPoint;
import ua.mai.servs.mod.bbb.security.services.BasicTokenService;
import ua.mai.servs.mod.bbb.security.services.JwtTokenService;
import ua.mai.servs.mod.bbb.security.services.TokenService;
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
        ClientConfig.class,
        JwtConfigurer.class,
})
@EnableConfigurationProperties({
      JwtProperties.class,
})
@Configuration
public class JwtConfig {

    private final JwtProperties jwtProperties;

    public JwtConfig(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Bean
    public TokenService jwtTokenService() {
        return new JwtTokenService(jwtProperties);
    }

    @Bean
    public BasicTokenService basicTokenService() {
        return new BasicTokenService((JwtTokenService) jwtTokenService(), jwtProperties);
    }

    @Bean
    public JwtAuthEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthEntryPoint();
    }

    @Bean
    public JwtAccessDeniedHandler jwtAccessDeniedHandler() {
        return new JwtAccessDeniedHandler();
    }

    @Bean
    public AuthController authController() {
        return new AuthController(basicTokenService());
    }

    public FeignErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public GlobalResponseEntityExceptionHandler restExceptionHandler() {
        return new GlobalResponseEntityExceptionHandler();
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
