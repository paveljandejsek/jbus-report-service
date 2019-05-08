package com.gdtorrent.jbusreportservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class GitHubService {

    private final RestTemplate restTemplate;

    public void postReportsComment(String pullRequestNumber, String reportsUrl) {
        restTemplate.postForObject("/issues/{pullRequestNumber}/comments", getRequestBody(reportsUrl), String.class, pullRequestNumber);
    }

    private String getRequestBody(String reportsUrl) {
        return "{\"body\": \"Generated [reports](" + reportsUrl + ")\"}";
    }

}
