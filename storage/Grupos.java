package storage;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Grupos {
    private static final String DATABASE_URL = "jdbc:sqlite:CofreDigital.db";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/api/grupos/add", Grupos::AddGrupo);
        server.createContext("/api/grupos/get", Grupos::GetGrupos);
        server.createContext("/api/grupos/update", Grupos::UpdateGrupo);
        server.createContext("/api/grupos/delete", Grupos::DeleteGrupo);
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port " + PORT);
    }

    public static void AddGrupo(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(requestBody, JsonObject.class);
                String nome = json.get("nome").getAsString();

                String sql = "INSERT INTO Grupos (nome) VALUES (?)";
                try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                        PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, nome);
                    pstmt.executeUpdate();
                }

                String response = "Grupo added successfully";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
                String response = "Error adding grupo";
                exchange.sendResponseHeaders(500, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }

    public static void GetGrupos(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                StringBuilder result = new StringBuilder();
                String sql = "SELECT * FROM Grupos";

                try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        ResultSet rs = pstmt.executeQuery()) {

                    while (rs.next()) {
                        int gid = rs.getInt("GID");
                        String nome = rs.getString("nome");

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("GID", gid);
                        jsonObject.addProperty("nome", nome);

                        result.append(jsonObject.toString()).append("\n");
                    }
                }

                String response = result.toString();
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
                String response = "Error fetching grupos";
                exchange.sendResponseHeaders(500, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }

    public static void UpdateGrupo(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(requestBody, JsonObject.class);

                int gid = json.get("GID").getAsInt();
                String nome = json.get("nome").getAsString();

                String sql = "UPDATE Grupos SET nome = ? WHERE GID = ?";
                try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                        PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, nome);
                    pstmt.setInt(2, gid);
                    pstmt.executeUpdate();
                }

                String response = "Grupo updated successfully";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
                String response = "Error updating grupo";
                exchange.sendResponseHeaders(500, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }

    public static void DeleteGrupo(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(requestBody, JsonObject.class);

                int gid = json.get("GID").getAsInt();

                String sql = "DELETE FROM Grupos WHERE GID = ?";
                try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                        PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, gid);
                    pstmt.executeUpdate();
                }

                String response = "Grupo deleted successfully";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
                String response = "Error deleting grupo";
                exchange.sendResponseHeaders(500, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }
}
