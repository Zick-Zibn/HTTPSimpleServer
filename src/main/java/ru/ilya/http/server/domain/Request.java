package ru.ilya.http.server.domain;

import java.util.Map;

public class Request {

    private RequestType requestType;

    private Map<String, String> headers;

    private String body;

    public Request() {
    }

    public Request(RequestType requestType, Map<String, String> headers, String body) {
        this.requestType = requestType;
        this.headers = headers;
        this.body = body;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
