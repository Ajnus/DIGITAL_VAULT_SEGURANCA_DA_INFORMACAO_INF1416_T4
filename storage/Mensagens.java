package storage;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
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

public class Mensagens {
    private static final String DATABASE_URL = "jdbc:sqlite:CofreDigital.db";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/api/mensagens/add", Mensagens::AddMensagem);
        server.createContext("/api/mensagens/get", Mensagens::GetMensagens);
        server.createContext("/api/mensagens/update", Mensagens::UpdateMensagem);
        server.createContext("/api/mensagens/delete", Mensagens::DeleteMensagem);

        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port " + PORT);
    }

    public static void AddMensagem(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(requestBody, JsonObject.class);

                String conteudo = json.get("conteudo").getAsString();

                String sql = "INSERT INTO Mensagens (conteudo) VALUES (?)";
                try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                        PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, conteudo);
                    pstmt.executeUpdate();
                }

                String response = "Mensagem added successfully";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
                String response = "Error adding mensagem";
                exchange.sendResponseHeaders(500, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }

    public static void GetMensagens(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                StringBuilder result = new StringBuilder();
                String sql = "SELECT * FROM Mensagens";

                try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        ResultSet rs = pstmt.executeQuery()) {

                    while (rs.next()) {
                        int mid = rs.getInt("MID");
                        String conteudo = rs.getString("conteudo");

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("MID", mid);
                        jsonObject.addProperty("conteudo", conteudo);

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
                String response = "Error fetching mensagens";
                exchange.sendResponseHeaders(500, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }

    public static void UpdateMensagem(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(requestBody, JsonObject.class);

                int mid = json.get("MID").getAsInt(); // ID da mensagem a ser atualizada
                String conteudo = json.get("conteudo").getAsString();

                String sql = "UPDATE Mensagens SET conteudo = ? WHERE MID = ?";
                try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                        PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, conteudo);
                    pstmt.setInt(2, mid);
                    pstmt.executeUpdate();
                }

                String response = "Mensagem updated successfully";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
                String response = "Error updating mensagem";
                exchange.sendResponseHeaders(500, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }

    public static void DeleteMensagem(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(requestBody, JsonObject.class);
    
                int mid = json.get("MID").getAsInt(); // ID da mensagem a ser excluída
    
                String sql = "DELETE FROM Mensagens WHERE MID = ?";
                try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, mid);
                    pstmt.executeUpdate();
                }
    
                String response = "Mensagem deleted successfully";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
                String response = "Error deleting mensagem";
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
