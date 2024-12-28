package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
public class HandleClientCalls extends Thread {
    InetAddress clientAddress;
    int clientPort;
    String clientData;
    String path;
    Socket socket;

    public HandleClientCalls(Socket socket, String clientData) {
        this.socket = socket;
        this.clientData = clientData;
        if (clientData != null) {
            
        
            String[] parts = clientData.split(" ");

            this.path = parts[1];
        }else{
            this.path = "";
        }
    }

     @Override
    public void run() {
        System.out.println("handler thread: port: " + clientPort +" address: " + clientAddress+ " data: " + clientData);
        System.out.println("path: " + path);
        System.out.println(getTypeOfFile(path+ "             fdsfdsfdssssssssssssssssssssssssssssssssssssssssssssss"));
        checkEndpoint(getTypeOfFile(path));
        
    }

    private void checkEndpoint(String type){
        String fileToFind = "src//main//resources//html//404.html";
        String contentType = "text/html";
        /*
        if (path.equals("/")) {
        fileToFind = "src//main//resources//html//home.html";
        }else if(path.equals("/js/home.js")){
        contentType = "application/javascript";
        fileToFind = "src//main//resources//js/home.js";
        }else if (path.equals("/home.css")) {
        //System.out.println("äädfsääfdsäfdäsfädsäfdsäfdsäfädsfädsäfdsäfds");
        contentType = "text/css";
        fileToFind = "src//main//resources//css/home.css";
        }
         */
        switch (type) {
            case "js" -> {
                contentType = "application/javascript";
                fileToFind = jsEndPoints();
            }
            case "css" -> {
                contentType = "text/css";
                fileToFind = cssEndPoints();
            }
            case "ico" -> System.out.println("ico xd");
            default -> {
                contentType = "text/html";
                fileToFind = htmlEndPoints();
            }
        }

        try{
            sendHtmlResponse(fileToFind,contentType);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    private String htmlEndPoints(){
        if (this.path.equals("/")) {
            return "src//main//resources//html//home.html";
        }
        return "src//main//resources//html//404.html";
    }
    private String cssEndPoints(){
        if (this.path.equals("/home.css")) {
            return "src//main//resources//css/home.css"; 
        }
        return "";
    }
    private String jsEndPoints(){
        if (this.path.equals("/js/home.js")) {
            return "src//main//resources//js/home.js";
        }
        return "";
    }
    private String getTypeOfFile(String path){
        int dotIndex = path.lastIndexOf('.');
        if (dotIndex == -1) {
            return ""; 
        }
        return path.substring(dotIndex + 1).toLowerCase();
    }
    private void sendHtmlResponse(String fileName,String contentType) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);
        BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
        String line;
        StringBuilder htmlContent = new StringBuilder();
        while ((line = fileReader.readLine()) != null) {
            htmlContent.append(line).append("\n");
        }
        // Send the HTTP response headers
        writer.println("HTTP/1.1 200 OK");  // Status code
        writer.println("Content-Type: " + contentType +";  charset=UTF-8");  // Content type for HTML
        writer.println();  // Blank line separating headers and body

        // Send the HTML response body
        writer.println(htmlContent.toString());

        writer.flush();  // Ensure the data is sent
        fileReader.close(); 
        socket.close();
    }

}