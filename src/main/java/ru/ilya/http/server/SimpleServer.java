package ru.ilya.http.server;

import ru.ilya.http.server.domain.Request;
import ru.ilya.http.server.service.*;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;


public class SimpleServer {

    private final Configuration configuration;

    private final FileService fileService;

    public SimpleServer(String[] args) {
        this.configuration = new Configuration(args);
        this.fileService = new FileService(configuration.getRootFolder());
    }

    public void runServer() {
        try (ServerSocket serverSocket = new ServerSocket(configuration.getPort())) {
            System.out.printf("Server started at port %d%n", configuration.getPort());

            while (true) {
                try {
                    Socket socket = serverSocket.accept();

                    System.out.println("New connection accepted");
                    ClientSocketService clientSocketService = new ClientSocketService(
                            socket,
                            new RequestParser(),
                            new ResponseSerializer());

                    Request request = clientSocketService.readRequest();
                    Path filePath = Paths.get(request.getFilename());
                    if (fileService.isFileExists(filePath)) {
                        try (InputStream inputStream = fileService.readFile(filePath)) {
                        }
                        // TODO complete request handling
                        // TODO Response response = new Response(200, )
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
