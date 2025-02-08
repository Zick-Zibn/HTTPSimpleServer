package ru.ilya.http.server.service;

import ru.ilya.http.server.domain.Request;
import ru.ilya.http.server.domain.Response;

import java.net.Socket;

public class ClientSocketService {

    private final Socket socket;
    private final RequestParser requestParser;
    private final ResponseSerializer responseSerializer;

    public ClientSocketService(
            Socket socket,
            RequestParser requestParser,
            ResponseSerializer responseSerializer
    ) {
        this.socket = socket;
        this.requestParser = requestParser;
        this.responseSerializer = responseSerializer;
    }

    public Request readRequest() {
        // TODO read request from socket
        String rawRequest = "";
        return requestParser.parse(rawRequest);
    }

    public void writeResponse(Response response) {
        String rawResponse = responseSerializer.serialize(response);
        // TODO write raw response to socket
    }
}
