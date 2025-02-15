package ru.ilya.http.server.service;

import ru.ilya.http.server.domain.Request;
import ru.ilya.http.server.domain.RequestType;

import java.util.*;

public class RequestParser {

    public Request parse(String rawRequest) {
        Request request = new Request();
        List<String> stringList = rawRequest.lines().toList();

        String firstLine = stringList.getFirst();
        String[] requestParams = firstLine.split(" ");
        request.setRequestType(RequestType.valueOf(requestParams[0]));
        request.setFilename(requestParams[1]);
        request.setHttpVersion(requestParams[2]);

        int i;
        for (i = 1; i < stringList.size(); i++) {
            String s = stringList.get(i);
            if (s.isBlank()) {
                break;
            }
            String[] arrString = s.split(": ");
            request.getHeaders().put(arrString[0], arrString[1]);
        }

        // TODO concat all lines from index i to save them into request.body

        return request;
    }
}
