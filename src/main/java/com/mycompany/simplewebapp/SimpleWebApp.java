package com.mycompany.simplewebapp;

import java.io.IOException;

public class SimpleWebApp {
    public static void main(String[] args) throws IOException {
        // Start the HTTP server on port 8081
        SimpleHttpService.startServer(8081);
    }
}
