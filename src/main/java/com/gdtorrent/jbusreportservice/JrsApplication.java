package com.gdtorrent.jbusreportservice;

import com.gdtorrent.jbusreportservice.property.GitHubProperties;
import com.gdtorrent.jbusreportservice.property.ReportServiceProperties;
import com.gdtorrent.jbusreportservice.property.ZipProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ ReportServiceProperties.class, GitHubProperties.class, ZipProperties.class })
public class JrsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JrsApplication.class, args);
    }

}
