package com.gdtorrent.jbusreportservice.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "report-service.github")
public class GitHubProperties {

    private String token;

    private String repositoryApiUrl;

}
