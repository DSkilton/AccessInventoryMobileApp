package com.mycompany.simplewebapp;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleHttpService {
    private static HttpServer server;
    
    public static void startServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        
        System.out.println("Server started at http://localhost:" + port);
        
        registerEndPoints(); // calls our registerEndPoints method on line 24
        server.setExecutor(null);
        server.start(); // starts the server
    }    
    
    public static void stopServer() {
        server.stop(0); // stops the server 
    }
    
    private static void registerEndPoints() {
        //this is where we can add our web end points
        server.createContext("/item", new ItemsHandler()); 
        server.createContext("/", new StaticPageHandler()); //sets localhost:8081 to the web page on StaticHandler
    }
}
