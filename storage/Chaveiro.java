package com.yourcompany;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Chaveiro {
    private static final String DATABASE_URL = "jdbc:sqlite:CofreDigital.db;";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/api/users/add", new AddUserHandler());
        server.createContext("/api/chaveiro/add", new AddChaveiroHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port " + PORT);
    }

    static class AddUserHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    String requestBody = new String(exchange.getRequestBody().readAllBytes());
                    Gson gson = new Gson();
                    JsonObject json = gson.fromJson(requestBody, JsonObject.class);

                    String loginName = json.get("login_name").getAsString();
                    String nome = json.get("nome").getAsString();
                    String senha = json.get("senha").getAsString();
                    int gid
