package com.gdtorrent.jbusreportservice.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "reports")
public class UploadController {

    @PostMapping(path = "{pullRequest}")
    public ResponseEntity uploadReports(@PathVariable("pullRequest") String pullRequest, @RequestParam("file") MultipartFile zipFile) {
        if (!"application/zip".equals(zipFile.getContentType())) {
            return ResponseEntity.badRequest().body("Invalid content type");
        }


        // todo unzip file
        // todo store files
        // todo comment to github

        return ResponseEntity.ok().build();
    }

}
