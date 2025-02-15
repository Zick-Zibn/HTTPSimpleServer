package ru.ilya.http.server;

import ru.ilya.http.server.domain.Request;
import ru.ilya.http.server.domain.Response;
import ru.ilya.http.server.service.ClientSocketService;
import ru.ilya.http.server.service.Configuration;
import ru.ilya.http.server.service.RequestParser;
import ru.ilya.http.server.service.ResponseSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SimpleServer {

    private static final int PORT = 8080;
    private static String filePath = null;
    private ClientSocketService clientSocketService;
    private Request request;
    RequestParser requestParser;

    public static void main(String[] args) throws IOException {
        Configuration configuration = new Configuration(args);
        if (configuration.checkParameters())
            new SimpleServer().runServer();
    }
    public void runServer(){
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.printf("Server started at port %d%n", PORT);

            while (true) {
                try {
                    Socket socket = serverSocket.accept();

                    System.out.println("New connection accepted");
                    Thread.ofVirtual().start(() -> {
                        clientSocketService = new ClientSocketService(socket, new RequestParser(), new ResponseSerializer());
                        clientSocketService.runService();
                    });
                    /*BufferedReader input = new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream(), StandardCharsets.UTF_8));

                    input.read();
                    while (!input.ready()) ;
                    while (input.ready()) {
                        String line = input.readLine();
                        System.out.println(line);
                    }

                    if (input.ready()) {

                    while (input.ready()) {
                        Stream<?> lines = input.lines();
                        //lines.map(l -> l.s).collect(Collectors.toMap((k, v) ->))
                        String line = input.readLine();
                        String[] arrString = line.split(" ");

                        if (arrString[0].equals("GET")) {
                            filePath = arrString[1];
                        }
                        System.out.println(line);
                    }

                    Thread.ofVirtual().start(() -> {
                        try {
                            new ClientService(socket, filePath).sendResponse();
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    });*/
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
