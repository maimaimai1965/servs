package ua.mai.servs.bootstrap;

import org.jooq.tools.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.core.env.Environment;
import ua.mai.servs.bootstrap.config.DefaultProfileUtil;
import ua.mai.servs.common.CustomRequestResponseLoggerFilter;
import ua.mai.servs.config.ModAaaConfig;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication(
        scanBasePackageClasses = {
//                AaClientConfiguration.class,
                ModAaaConfig.class,
        }, exclude = {SecurityAutoConfiguration.class})
//@SpringBootApplication(scanBasePackages = "ua.mai.servs.mod.aaa")
public class ModAaaApplication {

    private static final Logger log = LoggerFactory.getLogger(ModAaaApplication.class);

    private final Environment env;

    public ModAaaApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        System.getProperties().setProperty("spring.application.name", "SERVS: Aaa Module Services");
        SpringApplication app = new SpringApplication(ModAaaApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
new CustomRequestResponseLoggerFilter();
    }

    private static void logApplicationStartup(Environment env) {
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