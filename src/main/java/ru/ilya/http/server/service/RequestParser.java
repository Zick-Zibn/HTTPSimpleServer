package ru.ilya.http.server.service;

import ru.ilya.http.server.domain.Request;
import ru.ilya.http.server.domain.RequestType;

import java.util.*;

public class RequestParser {
    private String filePath;
    public Request parse(String rawRequest) {
        // TODO parse input string here to create Response object
        RequestType requestType = null;
        String body = "";

        List<String> stringList = rawRequest.lines().toList();
        Map<String,String> headers = new HashMap<>();

        for(String s: stringList){
            if (s.startsWith("GET")){
                requestType = RequestType.GET;
                this.filePath = s.split(" ")[1];
                continue;
            }
            if(!s.isBlank()) {
                String[] arrString = s.split(": ");
                headers.put(arrString[0], arrString[1]);
            }
        }
        return new Request(requestType, headers, body);
    }
    public String getFilePath() {
        return filePath.substring(1, filePath.length());
    }
}
