package com.mycompany.simplewebapp;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticPageHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            // Load the HTML file
            Path htmlPath = Paths.get("src/web/index.html");
            if (Files.exists(htmlPath)) {
                byte[] htmlBytes = Files.readAllBytes(htmlPath);
                exchange.sendResponseHeaders(200, htmlBytes.length);
                exchange.getResponseBody().write(htmlBytes);
                exchange.getResponseBody().close();
            } else {
                exchange.sendResponseHeaders(404, -1); // File not found
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }
}
