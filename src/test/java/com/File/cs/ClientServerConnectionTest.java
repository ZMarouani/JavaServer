package com.File.cs;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientServerConnectionTest {

    @Test
    void clientServerTest() throws IOException {
        final String ip = "127.0.0.1" ;
        final int port  = 6666 ;
        final ServerSocket serverSocket = new ServerSocket(6666);
        final File serverFile = new File("./");
        final FileServer server = new FileServer() ;

        server.run(serverSocket , serverFile);

        final Socket socket = new Socket( ip , port ) ;
        final Client client = new Client() ;

        client.run(socket);
        String response = client.sendMessage("this is a test message") ;

        assertEquals("this is a test message", response);

    }
}
