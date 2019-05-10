package com.gdtorrent.jbusreportservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.gdtorrent.jbusreportservice.property.RestProperties;
import com.gdtorrent.jbusreportservice.property.ZipProperties;
import com.gdtorrent.jbusreportservice.service.CleanupService;
import com.gdtorrent.jbusreportservice.service.GitHubService;
import com.gdtorrent.jbusreportservice.service.ZipService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

    @Autowired
    private ZipProperties zipProperties;

    @Autowired
    private CleanupService cleanupService;

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
        Resource zipResource = resourceLoader.getResource("classpath:reports.zip");
        MockMultipartFile file = new MockMultipartFile("file", "reports.zip", "application/zip", zipResource.getInputStream());

        MockHttpServletRequestBuilder httpServletRequestBuilder = MockMvcRequestBuilders.multipart("/api/reports/83")
                .file(file)
                .with(httpBasic(restProperties.getUsername(), restProperties.getPassword()));

        mvc.perform(httpServletRequestBuilder)
                .andExpect(authenticated())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldCleanupReports() throws IOException {
        String reportDirectory = "extractTest";
        Resource zipResource = resourceLoader.getResource("classpath:reports.zip");
        zipService.unzip(zipResource.getInputStream(), reportDirectory);

        Files.walk(Path.of(zipProperties.getDirectory() + reportDirectory))
                .map(Path::toFile)
                .forEach(file -> file.setLastModified(LocalDateTime.now().minusYears(1).toEpochSecond(ZoneOffset.UTC)));

        cleanupService.reportCleanup();

        assertThat(new File(zipProperties.getDirectory() + reportDirectory).exists()).isFalse();
    }

}
