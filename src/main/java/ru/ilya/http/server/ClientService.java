package ru.ilya.http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClientService {
    private final Socket socket;
    private Path filePath;
    private String requestPath;

    public ClientService(Socket socket, String requestPath){
        this.requestPath = requestPath;
        this.socket = socket;
    }
    public void sendResponse() throws IOException {

        try {
            this.filePath = this.getFileName(requestPath);
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            if (filePath != null) {
                output.println("HTTP/1.1 200 OK");
            }
            else {
                filePath = Path.of(SimpleServer.class.getResource("/NotFound.txt").toURI());
                output.println("HTTP/1.1 404 ERROR");
            }
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();

            BufferedReader inputFile = new BufferedReader(Files.newBufferedReader(Path.of(filePath.toUri())));

            while (inputFile.ready()) {
                output.println(inputFile.readLine());
            }
            output.flush();
        }
        catch(IOException | URISyntaxException e){
            System.out.println(e.toString());
        }
        finally {
            socket.close();
        }
    }

    private Path getFileName(String filePath) throws URISyntaxException {
        Path    fileResorce = null;
        URL resource;
        if (filePath.equals("/")) {
            fileResorce = Path.of(SimpleServer.class.getResource("/Main.txt").toURI());
        }
        else {
            URL url = SimpleServer.class.getResource(filePath);
            if (url != null) {
                fileResorce = Path.of(SimpleServer.class.getResource(filePath).toURI());
            }
        }
        return fileResorce;
    }
}
