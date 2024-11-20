package com.mycompany.simplewebapp;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleHttpService {
    private static HttpServer server;
    
    public static void startServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        
        System.out.println("Server started at http://localhost:" + port);
        
        registerEndPoints();
        server.setExecutor(null);
        server.start();
    }    
    
    public static void stopServer() {
        server.stop(0);
    }
    
    private static void registerEndPoints() {
        //this is where we can add our web end points
        server.createContext("/hello", new HelloHandler());
        server.createContext("/item", new ItemsHandler());
        server.createContext("/student", new StudentHandler());
        server.createContext("/", new StaticPageHandler());
    }
}
