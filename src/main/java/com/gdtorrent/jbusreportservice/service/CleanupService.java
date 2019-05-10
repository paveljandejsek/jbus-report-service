package com.gdtorrent.jbusreportservice.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

import com.gdtorrent.jbusreportservice.property.ZipProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

@Service
@RequiredArgsConstructor
public class CleanupService {

    @Value("${report-service.cleanup.max-days}")
    private int cleanupDays;

    private final ZipProperties zipProperties;

    @Scheduled(cron = "${report-service.cleanup.cron}")
    public void reportCleanup() {
        File reportsDirectory = new File(zipProperties.getDirectory());
        Arrays.stream(reportsDirectory.listFiles())
                .filter(file -> LocalDateTime.now().minusDays(cleanupDays).isAfter(LocalDateTime.ofEpochSecond(file.lastModified(), 0, ZoneOffset.UTC)))
                .forEach(FileSystemUtils::deleteRecursively);
    }

}
