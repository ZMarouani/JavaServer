package com.File.cs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private PrintWriter outputPrintWriter ;
    private BufferedReader inputPrintWriter ;

    public void run(final Socket socket) throws IOException {
        try(OutputStream outputStream = socket.getOutputStream()) {
            outputPrintWriter = new PrintWriter(outputStream);
        }

       try(InputStreamReader inputStream = new InputStreamReader(socket.getInputStream())){
           inputPrintWriter = new BufferedReader(inputStream);
       }
    }

    public String sendMessage(String message) throws  IOException {
        outputPrintWriter.println(message) ;

        return inputPrintWriter.readLine() ;
    }
}
