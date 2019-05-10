package com.gdtorrent.jbusreportservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JrsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JrsApplication.class, args);
    }

}
