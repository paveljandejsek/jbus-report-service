package com.gdtorrent.jbusreportservice;

import com.gdtorrent.jbusreportservice.property.RestProperties;
import com.gdtorrent.jbusreportservice.support.JrsAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Configuration
    @RequiredArgsConstructor
    public static class PublicApiSecurityConfigurer extends WebSecurityConfigurerAdapter {

        private final JrsAuthenticationEntryPoint authenticationEntryPoint;
        private final RestProperties restProperties;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf()
                    .disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .and()
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .httpBasic();
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser(restProperties.getUsername())
                    .password(new BCryptPasswordEncoder().encode(restProperties.getPassword()))
                    .authorities("ROLE_USER");
        }
    }

}
