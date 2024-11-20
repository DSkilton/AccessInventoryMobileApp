package com.mycompany.simplewebapp;

import java.awt.Button;
import java.awt.Label;
import java.awt.Panel;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SimpleWebApp {

    public static void main(String[] args) throws IOException {
        SimpleHttpService.startServer(8081);

    }
}

