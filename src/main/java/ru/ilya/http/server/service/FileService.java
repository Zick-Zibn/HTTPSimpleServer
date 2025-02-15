package ru.ilya.http.server.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {

    private final Path rootPath;

    public FileService(Path rootPath) {
        this.rootPath = rootPath;
    }

    public boolean isFileExists(Path filePath) {
        // TODO implement check if file exists
        return Files.exists(rootPath.resolve(filePath));
        //return false;
    }

    public InputStream readFile(Path fileName) throws IOException {
        // TODO implement file read to stream
        return Files.newInputStream(Path.of(rootPath.resolve(fileName).toUri()));
    }
}
