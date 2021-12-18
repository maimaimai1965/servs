package ua.mai.servs.common;

import org.jooq.tools.StringUtils;
import org.slf4j.Logger;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class AppUtil {

    public static void logApplicationStartup(Environment env, Logger log) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                    "Application '{}' is running! Access URLs:\n\t" +
                    "Local: \t\t{}://localhost:{}{}\n\t" +
                    "External: \t{}://{}:{}{}\n\t" +
                    "Profile(s): \t{}\n----------------------------------------------------------",
              env.getProperty("spring.application.name"),
              protocol, serverPort, contextPath,
              protocol, hostAddress, serverPort, contextPath,
              env.getActiveProfiles());
    }

}
