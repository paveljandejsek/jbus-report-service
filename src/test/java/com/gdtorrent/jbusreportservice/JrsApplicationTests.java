package com.gdtorrent.jbusreportservice;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import com.gdtorrent.jbusreportservice.property.RestProperties;
import com.gdtorrent.jbusreportservice.service.GitHubService;
import com.gdtorrent.jbusreportservice.service.ZipService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JrsApplicationTests {

    @Autowired
    private GitHubService gitHubService;

    @Autowired
    private ZipService zipService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RestProperties restProperties;

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

    @Test
    public void shouldNotAuthorize() throws Exception {
        MockHttpServletRequestBuilder httpServletRequestBuilder = post("/api/reports");
        ResultActions resultActions = mvc.perform(httpServletRequestBuilder);

        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldUploadReportsAndComment() throws Exception {
        MockHttpServletRequestBuilder httpServletRequestBuilder = post("/api/reports/83")
                .with(httpBasic(restProperties.getUsername(), restProperties.getPassword()));
        ResultActions resultActions = mvc.perform(httpServletRequestBuilder);

        resultActions.andExpect(authenticated());

        // todo send zip
        // todo add cron scheduled deleting of old reports
    }

    @Test
    public void shouldFailValidations() {
        // TODO
    }

}
