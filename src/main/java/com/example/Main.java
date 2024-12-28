package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");

        try (
            ServerSocket serverSocket = new ServerSocket(8000)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("New client connected: " + socket.getInetAddress());
                    System.out.println("get client port: "+socket.getPort());
                    System.out.println(socket.toString());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    
                    String data = reader.readLine();
                    System.out.println("data aaaaaa: "+ data);
                    HandleClientCalls handleClientCalls = new HandleClientCalls(socket,data);
                    handleClientCalls.start();
                }
        }

        
    }
}
