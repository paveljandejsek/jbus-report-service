package com.gdtorrent.jbusreportservice.property;

import java.util.List;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "report-service.zip")
public class ZipProperties {

    private String directory;

    private List<String> allowedEntryExtensions;

}
