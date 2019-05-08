package com.gdtorrent.jbusreportservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class JbusReportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JbusReportServiceApplication.class, args);
    }

}
