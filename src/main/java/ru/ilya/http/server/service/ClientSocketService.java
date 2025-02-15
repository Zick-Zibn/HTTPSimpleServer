package ru.ilya.http.server.service;

import ru.ilya.http.server.domain.*;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ClientSocketService {

    private final Socket socket;
    private final RequestParser requestParser;
    private final ResponseSerializer responseSerializer;
    private Request request;
    private Response response;
    private BufferedReader bufferedReader;

    public ClientSocketService(
            Socket socket,
            RequestParser requestParser,
            ResponseSerializer responseSerializer
    ) {
        this.socket = socket;
        this.requestParser = requestParser;
        this.responseSerializer = responseSerializer;
    }

    public Request readRequest() throws IOException {
        // TODO read request from socket
        StringBuilder textBuilder = new StringBuilder();
        while (bufferedReader.ready()) {
            textBuilder.append((char) bufferedReader.read());
        }
        String rawRequest = new String(textBuilder.toString());

        return requestParser.parse(rawRequest);
    }

    public void writeResponse(Response response) throws IOException {
        String rawResponse = responseSerializer.serialize(response);
        // TODO write raw response to socket
        PrintWriter output = new PrintWriter(socket.getOutputStream());
        output.println(rawResponse);
        output.flush();
    }

    public void createResponse(String filePath) throws IOException {
        HashMap<String, String> header = new HashMap<>();
        FileService fileService = new FileService(Path.of(Configuration.PATHFILESFOLDER));
        response = new Response();


        if (fileService.isFileExists(Path.of(requestParser.getFilePath()))) {
            InputStream inputStream = fileService.readFile(Path.of(requestParser.getFilePath()));
            response.setResponseCode(200);
            response.setBody(new String(inputStream.readAllBytes()));
        } else {
            response.setResponseCode(404);
        }
        header.put("HTTP/1.1", " " + String.valueOf(response.getResponseCode()));
        header.put("Content-Type:", " text/html; charset=utf-8");
        //header.put("Connection:", " Keep-Alive");
        response.setHeaders(header);

        this.writeResponse(response);
    }

    public void runService() {

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(
                                                socket.getInputStream(), StandardCharsets.UTF_8));
            while (!bufferedReader.ready()) ;

            if (bufferedReader.ready()) {
                request = this.readRequest();
                this.createResponse(requestParser.getFilePath());
                socket.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
