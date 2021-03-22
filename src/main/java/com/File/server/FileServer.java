package com.File.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public class FileServer {
    /**
     * Basic file server main entrypoint
     * @param serverSocket socket to accept connection
     * @param rootDir served file
     * @throws IOException I/O errors
     */
    public void run(final ServerSocket serverSocket, final File rootDir) throws IOException {
        while (true) {
            try (Socket socket = serverSocket.accept()) {
                final InputStream stream = socket.getInputStream();
                final InputStreamReader reader = new InputStreamReader(stream);
                final BufferedReader bufferedReader = new BufferedReader(reader);
                String inputLine = bufferedReader.readLine();

                assert inputLine != null;
                assert inputLine.startsWith("GET");

                final String path = inputLine.split(" ")[1];
                final File fullPath = new File(rootDir, path);

                try (final OutputStream outputStream = socket.getOutputStream()){
                    final PrintWriter printer = new PrintWriter(outputStream);

                    if (fullPath.isFile()) {
                        printer.write("HTTP/1.0 200 ok\r\n");
                        printer.write(".\r\n");
                        printer.write(new String(Files.readAllBytes(fullPath.toPath())) + "\r\n");
                        printer.flush();
                    } else {
                        printer.write("Http/1.0 404 Not Found\r\n");
                        printer.write(".\r\n");
                        printer.flush();
                        break ;
                    }
                }
            }
        }
    }
}
