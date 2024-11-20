package com.mycompany.simplewebapp;

import java.io.IOException;

public class SimpleWebApp {

    public static void main(String[] args) throws IOException {
        SimpleHttpService.startServer(8081);

    }
}

