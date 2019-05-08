package com.gdtorrent.jbusreportservice;

import com.gdtorrent.jbusreportservice.support.JrsAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Configuration
    @RequiredArgsConstructor
    public static class PublicApiSecurityConfigurer extends WebSecurityConfigurerAdapter {

        private final JrsAuthenticationEntryPoint authenticationEntryPoint;

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers(HttpMethod.OPTIONS, "/api/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            HttpSecurity httpSecurity = http.antMatcher("/api/**")
                    .csrf()
                    .disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .and();

            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity
                    .authorizeRequests();

            registry.antMatchers("/api/**").authenticated();
        }
    }

}
