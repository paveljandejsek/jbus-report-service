package com.gdtorrent.jbusreportservice.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("report-service")
public class ReportServiceProperties {

    private String reportsUrl;

    private GitHubProperties github;

    private ZipProperties zip;

}
