package com.gdtorrent.jbusreportservice.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "report-service.rest")
public class RestProperties {

    private String username;

    private String password;

}
