package ua.mai.servs.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;
import ua.mai.servs.common.AppUtil;
import ua.mai.servs.bootstrap.config.DefaultProfileUtil;
import ua.mai.servs.mod.aaa.config.ModAaaConfig;

@EnableFeignClients(basePackages = {"ua.mai.servs.mod.aaa.clients"})
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
        AppUtil.logApplicationStartup(env, log);
    }

}
