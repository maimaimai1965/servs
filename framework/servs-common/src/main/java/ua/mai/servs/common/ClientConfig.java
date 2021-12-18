package ua.mai.servs.common;

import com.google.gson.GsonBuilder;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Bean
    public Encoder encoder() {
        return new GsonEncoder(
                new GsonBuilder()
                        .setPrettyPrinting()
                        .create());
    }

    @Bean
    public Decoder decoder() {
        return new GsonDecoder(
                new GsonBuilder()
                        .setPrettyPrinting()
                        .disableHtmlEscaping().create());
    }
}
