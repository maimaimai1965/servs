package ua.mai.servs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.mai.servs.common.CustomRequestResponseLoggerFilter;

@Configuration
public class RequestLoggingFilterConfig {
//    @Bean
//    public CommonsRequestLoggingFilter logFilter() {
//        CustomCommonsRequestLoggingFilter filter
//                = new CustomCommonsRequestLoggingFilter();
//        filter.setBeforeMessagePrefix("REQ_IN start : ");
//        filter.setBeforeMessageSuffix("");
//        filter.setAfterMessagePrefix("REQ_IN finish : ");
//        filter.setAfterMessageSuffix("");
//        filter.setIncludeClientInfo(true);
//        filter.setIncludeQueryString(true);
//        filter.setIncludePayload(true);
//        filter.setMaxPayloadLength(10000);
//        filter.setIncludeHeaders(false);
//        return filter;
//    }

    @Bean
    public CustomRequestResponseLoggerFilter logFilter() {
        CustomRequestResponseLoggerFilter filter = new CustomRequestResponseLoggerFilter();
        filter.setBeforeRequestMessagePrefix("REQ_IN start  : ");
        filter.setBeforeRequestMessageSuffix("");
        filter.setAfterRequestMessagePrefix("REQ_IN finish : ");
        filter.setAfterRequestMessageSuffix("");
        filter.setIncludeRequestPayload(true);
        filter.setMaxRequestPayloadLength(10000);

        filter.setResponseMessagePrefix("RESP_OUT : ");
        filter.setResponseMessageSuffix("");
        filter.setIncludeResponsePayload(true);
        filter.setMaxResponsePayloadLength(10000);
        return filter;
    }
}
