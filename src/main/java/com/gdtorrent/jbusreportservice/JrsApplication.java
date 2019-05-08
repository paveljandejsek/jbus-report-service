package com.gdtorrent.jbusreportservice;

import com.gdtorrent.jbusreportservice.property.GitHubProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GitHubProperties.class)
public class JrsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JrsApplication.class, args);
    }

}
