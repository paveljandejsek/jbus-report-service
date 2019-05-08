package com.gdtorrent.jbusreportservice.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "report-service.github")
public class GitHubProperties {

    private String token;

    private String repositoryApiUrl;

}
