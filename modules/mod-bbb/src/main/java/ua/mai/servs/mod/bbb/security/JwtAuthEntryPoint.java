package ua.mai.servs.mod.bbb.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException {
        String message;
        if (exception.getCause() != null) {
            message = exception.getCause().getMessage();
        } else {
            message = exception.getMessage();
        }
        log.warn(message);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        byte[] body = new ObjectMapper()
                .writeValueAsBytes(Map.of(
                        "type", "https://www.jhipster.tech/problem/problem-with-message",
                        "title", "Forbidden",
                        "status", HttpServletResponse.SC_FORBIDDEN,
                        "detail", "Access is denied",
                        "message", "error.http.403",
                        "path", urlPathHelper.getPathWithinApplication(request)
                ));
        response.getOutputStream().write(body);
    }
}
