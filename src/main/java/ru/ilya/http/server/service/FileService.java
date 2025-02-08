package ru.ilya.http.server.service;

import java.io.InputStream;
import java.nio.file.Path;

public class FileService {

    private final Path rootPath;

    public FileService(Path rootPath) {
        this.rootPath = rootPath;
    }

    public boolean isFileExists(Path filePath) {
        // TODO implement check if file exists
        return false;
    }

    public InputStream readFile(Path filePath) {
        // TODO implement file read to stream
        return null;
    }
}
