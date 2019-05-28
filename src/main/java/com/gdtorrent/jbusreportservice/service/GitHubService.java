package com.gdtorrent.jbusreportservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GitHubService {

    private final RestTemplate restTemplate;

    public void postReportsComment(String pullRequestNumber, String reportsUrl) {
        log.info("calling /issues/{}/comments", pullRequestNumber);
        String requestBody = getRequestBody(reportsUrl + "/index.xml");
        log.info("with body {}", requestBody);
        String response = restTemplate.postForObject("/issues/{pullRequestNumber}/comments", requestBody, String.class, pullRequestNumber);
        log.info("got response: {}", response);
    }

    private String getRequestBody(String reportsUrl) {
        return "{\"body\": \"Generated [reports](" + reportsUrl + ")\"}";
    }

}
