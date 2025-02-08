package ru.ilya.http.server.service;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Configuration {

    public Configuration(String[] args) {
        // TODO pars parameters to extract port and path
    }

    public int getPort() {
        return 0;
    }

    public Path getRootPath() {
        return Paths.get("");
    }
}
