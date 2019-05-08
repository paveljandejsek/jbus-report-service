package com.gdtorrent.jbusreportservice.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class ZipService {

    @SneakyThrows(IOException.class)
    public void unzip(File zipFile, String destinationDirectoryPath) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                processEntry(destinationDirectoryPath, zis, zipEntry);
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        }
    }

    private void processEntry(String destinationDirectoryPath, ZipInputStream zis, ZipEntry zipEntry) throws IOException {
        byte[] buffer = new byte[1024];

        File newFile = newFile(destinationDirectoryPath, zipEntry);
        try (FileOutputStream fos = new FileOutputStream(newFile)) {
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        }
    }

    // zip slip prevention
    private File newFile(String destinationDirectoryPath, ZipEntry zipEntry) throws IOException {
        File destinationDirectory = new File(destinationDirectoryPath);
        File destinationFile = new File(destinationDirectory, zipEntry.getName());

        String directoryPath = destinationDirectory.getCanonicalPath();
        String filePath = destinationFile.getCanonicalPath();

        if (!filePath.startsWith(directoryPath + File.separator)) {
            throw new IOException("Entry is outside of the target directory: " + zipEntry.getName());
        }

        return destinationFile;
    }

}
