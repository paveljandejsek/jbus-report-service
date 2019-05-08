package com.gdtorrent.jbusreportservice;

import java.io.IOException;

import com.gdtorrent.jbusreportservice.service.GitHubService;
import com.gdtorrent.jbusreportservice.service.ZipService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JrsApplicationTests {

    @Autowired
    private GitHubService gitHubService;

    @Autowired
    private ZipService zipService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void contextLoads() {
    }

    @Test
    public void postsComments() {
        gitHubService.postReportsComment("83", "https://www.google.com");
    }

    @Test
    public void shouldExtractReportsZip() throws IOException {
        Resource zipResource = resourceLoader.getResource("classpath:reports.zip");
        zipService.unzip(zipResource.getInputStream(), "extractTest");
    }

}
