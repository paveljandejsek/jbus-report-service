package com.gdtorrent.jbusreportservice.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("report-service")
public class ReportServiceProperties {

    private String reportsUrl;

    private GitHubProperties github;

    private ZipProperties zip;

}
