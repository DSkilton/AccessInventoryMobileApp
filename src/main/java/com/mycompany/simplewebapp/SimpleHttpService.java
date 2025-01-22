package com.mycompany.simplewebapp;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleHttpService {

    public static void startServer(int port) throws IOException {
        // Create the HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("Server started at http://localhost:" + port);

        // Map endpoints to handlers
        server.createContext("/", new StaticPageHandler()); // Serve the HTML page
        server.createContext("/items", new ItemsHandler()); // Manage inventory operations

        // Start the server
        server.setExecutor(null); // Use the default executor
        server.start();
    }
}
