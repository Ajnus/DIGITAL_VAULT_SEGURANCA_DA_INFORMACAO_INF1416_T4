package storage;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Registros {
    private static final String DATABASE_URL = "jdbc:sqlite:CofreDigital.db";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/api/registros/add", Registros::AddRegistro);
        server.createContext("/api/registros/get", Registros::GetRegistros);
        server.createContext("/api/registros/update", Registros::UpdateRegistro);
        server.createContext("/api/registros/delete", Registros::DeleteRegistro);
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port " + PORT);
    }

    public static void AddRegistro(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(requestBody, JsonObject.class);

                int uid = json.get("UID").getAsInt();
                int mid = json.get("MID").getAsInt();
                String descricao = json.get("descricao").getAsString();

                String sql = "INSERT INTO Registros (UID, MID, descricao) VALUES (?, ?, ?)";
                try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                        PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, uid);
                    pstmt.setInt(2, mid);
                    pstmt.setString(3, descricao);
                    pstmt.executeUpdate();
                }

                String response = "Registro added successfully";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
                String response = "Error adding registro";
                exchange.sendResponseHeaders(500, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }

    public static void GetRegistros(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                StringBuilder result = new StringBuilder();
                String sql = "SELECT * FROM Registros";

                try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        ResultSet rs = pstmt.executeQuery()) {

                    while (rs.next()) {
                        int rid = rs.getInt("RID");
                        int uid = rs.getInt("UID");
                        int mid = rs.getInt("MID");
                        String descricao = rs.getString("descricao");
                        String data_hora = rs.getString("data_hora");

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("RID", rid);
                        jsonObject.addProperty("UID", uid);
                        jsonObject.addProperty("MID", mid);
                        jsonObject.addProperty("descricao", descricao);
                        jsonObject.addProperty("data_hora", data_hora);

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
                String response = "Error fetching registros";
                exchange.sendResponseHeaders(500, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }

    public static void UpdateRegistro(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(requestBody, JsonObject.class);

                int rid = json.get("RID").getAsInt(); // ID do registro a ser atualizado
                int uid = json.get("UID").getAsInt();
                int mid = json.get("MID").getAsInt();
                String descricao = json.get("descricao").getAsString();

                // Atualização do registro no banco de dados
                String sql = "UPDATE Registros SET UID = ?, MID = ?, descricao = ? WHERE RID = ?";
                try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                        PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, uid);
                    pstmt.setInt(2, mid);
                    pstmt.setString(3, descricao);
                    pstmt.setInt(4, rid);
                    pstmt.executeUpdate();
                }

                String response = "Registro updated successfully";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
                String response = "Error updating registro";
                exchange.sendResponseHeaders(500, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }

    public static void DeleteRegistro(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(requestBody, JsonObject.class);

                int rid = json.get("RID").getAsInt(); // ID do registro a ser excluído

                // Exclusão do registro no banco de dados
                String sql = "DELETE FROM Registros WHERE RID = ?";
                try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                        PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, rid);
                    pstmt.executeUpdate();
                }

                String response = "Registro deleted successfully";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
                String response = "Error deleting registro";
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
