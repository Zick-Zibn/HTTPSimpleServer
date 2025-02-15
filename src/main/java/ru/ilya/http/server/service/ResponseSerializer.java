package ru.ilya.http.server.service;

import ru.ilya.http.server.domain.Response;

import java.util.HashMap;
import java.util.Map;

public class ResponseSerializer {

    public String serialize(Response response) {
        StringBuilder   stringResponse = new StringBuilder("");
        // TODO implement HTTP response string creation based on response object from parameter
        Map<String, String> mapHeaders = response.getHeaders();
        for (Map.Entry<String, String> pair : mapHeaders.entrySet()) {
            stringResponse.append(pair.getKey() + pair.getValue() + "/r/n");
        }
        stringResponse.append("/r/n");
        stringResponse.append(response.getBody());

        return stringResponse.toString();
    }
}
