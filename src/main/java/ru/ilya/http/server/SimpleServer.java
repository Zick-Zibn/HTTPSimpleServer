package ru.ilya.http.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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

                    Thread.ofVirtual().start(() -> {
                        try {
                            new ClientService(socket, filePath).sendResponse();
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    });
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
