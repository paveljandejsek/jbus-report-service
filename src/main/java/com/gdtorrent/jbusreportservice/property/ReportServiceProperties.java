package com.gdtorrent.jbusreportservice.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("report-service")
public class ReportServiceProperties {

    private Reports reports;

    private GitHubProperties github;

    private ZipProperties zip;

    @Data
    public static class Reports {
        private String url;

        private String folder;
    }

}
