package ru.ilya.http.server.service;

import ru.ilya.http.server.domain.*;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientSocketService {

    private final RequestParser requestParser;
    private final ResponseSerializer responseSerializer;
    private final BufferedReader input;
    private final PrintWriter output;

    public ClientSocketService(
            Socket socket,
            RequestParser requestParser,
            ResponseSerializer responseSerializer
    ) throws IOException {
        this.input = new BufferedReader(new InputStreamReader(
                socket.getInputStream(), StandardCharsets.UTF_8));
        this.output = new PrintWriter(socket.getOutputStream());
        this.requestParser = requestParser;
        this.responseSerializer = responseSerializer;
    }

    public Request readRequest() throws IOException {
        while (!input.ready()); // TODO implement here timeout

        StringBuilder textBuilder = new StringBuilder();
        while (input.ready()) {
            // TODO why not bufferedReader.readLine();
            textBuilder.append((char) input.read());
        }
        String rawRequest = textBuilder.toString();

        return requestParser.parse(rawRequest);
    }

    public void writeResponse(Response response) {
        String rawResponse = responseSerializer.serialize(response);
        output.print(rawResponse); // what about extra new line at the end. May be just print()
        output.flush();
    }
}
