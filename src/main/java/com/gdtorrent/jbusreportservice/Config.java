package com.gdtorrent.jbusreportservice;

import com.gdtorrent.jbusreportservice.property.GitHubProperties;
import com.gdtorrent.jbusreportservice.property.ReportServiceProperties;
import com.gdtorrent.jbusreportservice.property.ZipProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config {

    @Bean
    public RestTemplate gitHubRestTemplate(GitHubProperties gitHubProperties, RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .additionalInterceptors((httpRequest, bytes, clientHttpRequestExecution) -> {
                    httpRequest.getHeaders().add(HttpHeaders.AUTHORIZATION, "token " + gitHubProperties.getToken());
                    return clientHttpRequestExecution.execute(httpRequest, bytes);
                })
                .rootUri(gitHubProperties.getRepositoryApiUrl())
                .build();
    }

    @RequiredArgsConstructor
    @Configuration
    public static class MvcConfigurer implements WebMvcConfigurer {

        private final ZipProperties zipProperties;
        private final ReportServiceProperties reportServiceProperties;

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
                    .addResourceHandler("/" + reportServiceProperties.getReports().getFolder() + "/**")
                    .addResourceLocations("file:" + zipProperties.getDirectory())
                    .setCacheControl(CacheControl.noCache());
        }

    }

}
