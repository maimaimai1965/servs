package ua.mai.servs.mod.bbb.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.mai.servs.mod.bbb.props.JwtProperties;
import ua.mai.servs.mod.bbb.security.JwtAccessDeniedHandler;
import ua.mai.servs.mod.bbb.security.JwtAuthTokenFilter;
import ua.mai.servs.mod.bbb.security.JwtAuthEntryPoint;
import ua.mai.servs.mod.bbb.security.services.JwtTokenService;
import ua.mai.servs.mod.bbb.security.services.TokenService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JwtConfigurer extends WebSecurityConfigurerAdapter {
    private final JwtTokenService jwtTokenService;
    private final JwtAuthEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtProperties jwtProperties;

    @Autowired
    public JwtConfigurer(JwtTokenService jwtTokenService, JwtAuthEntryPoint jwtAuthenticationEntryPoint,
                         JwtAccessDeniedHandler jwtAccessDeniedHandler, JwtProperties jwtProperties) {
        this.jwtTokenService = jwtTokenService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
              // Disable CSRF (cross site request forgery)
              .csrf().disable()
              .exceptionHandling()
              .authenticationEntryPoint(jwtAuthenticationEntryPoint)
              .accessDeniedHandler(jwtAccessDeniedHandler)
              .and()
              // No session will be created or used by spring security
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and()
              // Entry points
              .authorizeRequests()
              .mvcMatchers(jwtProperties.getEndpoint()).permitAll()
              // Disallow everything else.
              .anyRequest().authenticated()
              .and()
              .addFilterBefore(new JwtAuthTokenFilter(jwtTokenService),
                               UsernamePasswordAuthenticationFilter.class);
    }

}
