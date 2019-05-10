package com.gdtorrent.jbusreportservice.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.gdtorrent.jbusreportservice.property.ZipProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ZipService {

    private final ZipProperties zipProperties;

    @SneakyThrows(IOException.class)
    public void unzip(InputStream zipInputStream, String relativeDirectory) {
        String destinationDirectoryPath = zipProperties.getDirectory() + relativeDirectory;
        log.info("unzipping into {}", destinationDirectoryPath);
        File destinationDirectory = new File(destinationDirectoryPath);
        if (destinationDirectory.exists()) {
            log.info("destination directory already exists, removing", destinationDirectoryPath);
            FileSystemUtils.deleteRecursively(destinationDirectory);
        }
        destinationDirectory.mkdirs();

        try (ZipInputStream zis = new ZipInputStream(zipInputStream)) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                log.info("processing zip entry {}", zipEntry.getName());
                validateZipEntryExtension(zipEntry.getName());
                processEntry(destinationDirectory, zis, zipEntry);
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        }
    }

    private void validateZipEntryExtension(String zipEntryName) {
        zipProperties.getAllowedEntryExtensions().stream()
                .filter(zipEntryName::endsWith)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid zip entry extension for file: " + zipEntryName));
    }

    private void processEntry(File destinationDirectory, ZipInputStream zis, ZipEntry zipEntry) throws IOException {
        byte[] buffer = new byte[1024];

        File newFile = newFile(destinationDirectory, zipEntry);
        try (FileOutputStream fos = new FileOutputStream(newFile)) {
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        }
    }

    // zip slip prevention
    private File newFile(File destinationDirectory, ZipEntry zipEntry) throws IOException {
        File destinationFile = new File(destinationDirectory, zipEntry.getName());

        String directoryPath = destinationDirectory.getCanonicalPath();
        String filePath = destinationFile.getCanonicalPath();

        if (!filePath.startsWith(directoryPath + File.separator)) {
            throw new IOException("Entry is outside of the target directory: " + zipEntry.getName());
        }

        return destinationFile;
    }

}
