package ua.mai.servs.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import ua.mai.servs.common.AppUtil;
import ua.mai.servs.bootstrap.config.DefaultProfileUtil;
import ua.mai.servs.mod.bbb.config.ModBbbConfig;
import ua.mai.servs.mod.bbb.config.EmulatorConfig;
import ua.mai.servs.mod.bbb.props.JwtProperties;

@SpringBootApplication(
        scanBasePackageClasses = {
//                AaClientConfiguration.class,
              EmulatorConfig.class,
              ModBbbConfig.class,
        }, exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages={"ua.mai.servs.mod.bbb"})
//                             "ua.mai.servs.clients"})
public class ModBbbApplication {

    private static final Logger log = LoggerFactory.getLogger(ModBbbApplication.class);

    private final Environment env;

    public ModBbbApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        System.getProperties().setProperty("spring.application.name", "SERVS: Bbb Module Services");
        SpringApplication app = new SpringApplication(ModBbbApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        AppUtil.logApplicationStartup(env, log);
    }

}
