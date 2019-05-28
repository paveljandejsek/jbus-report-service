package com.gdtorrent.jbusreportservice;

import com.gdtorrent.jbusreportservice.property.ReportServiceProperties;
import com.gdtorrent.jbusreportservice.property.RestProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Configuration
    @RequiredArgsConstructor
    public static class PublicApiSecurityConfigurer extends WebSecurityConfigurerAdapter {

        private final RestProperties restProperties;
        private final PasswordEncoder passwordEncoder;
        private final ReportServiceProperties reportServiceProperties;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf()
                        .disable()
                    .exceptionHandling()
                    .and()
                    .authorizeRequests()
                        .antMatchers("/" + reportServiceProperties.getReports().getFolder() + "/**").permitAll()
                        .antMatchers("/**").authenticated()
                    .and()
                    .httpBasic()
                        .realmName("JRS Realm");
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser(restProperties.getUsername())
                    .password(passwordEncoder.encode(restProperties.getPassword()))
                    .authorities("ROLE_USER");
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
