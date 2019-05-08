package com.gdtorrent.jbusreportservice.rest;

import java.io.IOException;

import javax.validation.constraints.Pattern;

import com.gdtorrent.jbusreportservice.property.ReportServiceProperties;
import com.gdtorrent.jbusreportservice.service.GitHubService;
import com.gdtorrent.jbusreportservice.service.ZipService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping(path = "/api/reports")
public class UploadController {

    private final ReportServiceProperties reportServiceProperties;
    private final ZipService zipService;
    private final GitHubService gitHubService;

    @SneakyThrows(IOException.class)
    @PostMapping(path = "/{pullRequestNumber}")
    public ResponseEntity uploadReports(@PathVariable("pullRequestNumber") @Pattern(regexp = "\\d+") String pullRequestNumber,
            @RequestParam("file") MultipartFile zipFile) {
        if (!"application/zip".equals(zipFile.getContentType())) {
            return ResponseEntity.badRequest().body("Invalid content type");
        }

        zipService.unzip(zipFile.getInputStream(), pullRequestNumber);
        gitHubService.postReportsComment(pullRequestNumber, reportServiceProperties.getReportsUrl() + pullRequestNumber);

        return ResponseEntity.ok().build();
    }

}
