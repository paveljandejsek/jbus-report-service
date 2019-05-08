package com.gdtorrent.jbusreportservice;

import com.gdtorrent.jbusreportservice.property.GitHubProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JrsConfig {

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

}
