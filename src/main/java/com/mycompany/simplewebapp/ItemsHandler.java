/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simplewebapp;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Student
 */
public class ItemsHandler implements HttpHandler {

    private static Map<String, Integer> items = new HashMap<>();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        if ("GET".equalsIgnoreCase(requestMethod)) {
            handleGetRequest(exchange);
        } else if ("POST".equalsIgnoreCase(requestMethod)) {
            handlePostRequest(exchange);
        } else if ("DELETE".equalsIgnoreCase(requestMethod)) {
            handleDeleteRequest(exchange);
        } else {
            exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handleGetRequest(HttpExchange exchange) throws IOException {
        String response = items.entrySet().stream()
                .map(entry -> "\"" + entry.getKey() + "\":" + entry.getValue())
                .collect(Collectors.joining(", ", "{", "}"));
        exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void handlePostRequest(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8).trim();
        String[] parts = requestBody.split(",");
        if (parts.length == 2) {
            String itemName = parts[0].trim();
            int qty = Integer.parseInt(parts[1].trim());
            items.put(itemName, items.getOrDefault(itemName, 0) + qty);
            exchange.sendResponseHeaders(201, -1); // Created
        } else {
            exchange.sendResponseHeaders(400, -1); // Bad Request
        }
    }

    private void handleDeleteRequest(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        String itemName = query != null && query.startsWith("name=") ? query.substring(5) : null;
        if (itemName != null && items.containsKey(itemName)) {
            items.remove(itemName);
            exchange.sendResponseHeaders(200, -1); // OK
        } else {
            exchange.sendResponseHeaders(404, -1); // Not Found
        }
    }
}
