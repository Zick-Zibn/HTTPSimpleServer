package HTTPServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class SimpleServer {

    private static final int PORT = 8080;
    private static String filePath = null;
    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.printf("Server started at port %d%n", PORT);

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("New connection accepted");
                    BufferedReader input = new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream(), StandardCharsets.UTF_8));

                    while (!input.ready()) ;

                    while (input.ready()) {
                        String line = input.readLine();
                        String[] arrString = line.split(" ");

                        if (arrString[0].equals("GET")) {
                            filePath = arrString[1];
                        }
                        System.out.println(line);
                    }
                    OutputStream outputStream = socket.getOutputStream();
                    Thread.ofVirtual().start(() -> {
                        try {
                            new ClientResponce(socket, filePath).sendResponse();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
