package ua.mai.servs.mod.aaa.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Import;
import ua.mai.servs.config.ClientConfig;
import ua.mai.servs.config.RequestResponseLoggingFilterConfig;

import java.util.List;

@ConfigurationProperties(prefix = "servs.modules.aaa")
@Data
public class ModAaaProps {
    private String url;
//    private List<Service> services;

//    @Data
//    public static class Service {
//        private Endpoints login;
//        private String password;
//    }
//
//    @Data
//    public static class Endpoints {
//        private Operations operations;
//        private List<OperationMethodUri> operationMethodUri;
//    }
//
//    @Data
//    public static class Operations {
//        private String login;
//        private String corpmsisdns;
//    }
}
