package com.gdtorrent.jbusreportservice.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "reports")
public class UploadController {

    // todo add url to PR
    @PostMapping
    public ResponseEntity uploadReports(@RequestParam("file") MultipartFile zipFile) {
        // todo validate zip file
        // todo unzip file
        // todo store files
        // todo comment to github


        return ResponseEntity.ok().build();
    }

}
