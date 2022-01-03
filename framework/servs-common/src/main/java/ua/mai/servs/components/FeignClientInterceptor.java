package ua.mai.servs.components;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Autowired
    @Lazy
    private AccessTokenProvider accessTokenProvider;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (!requestTemplate.feignTarget().name().contains("auth-jwt")) {
            requestTemplate.header("Authorization", String.format("Bearer %s", accessTokenProvider.getJwtToken()));
        }
    }
}
