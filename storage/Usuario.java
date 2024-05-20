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
import java.sql.SQLException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Usuario {
    private static final String DATABASE_URL = "jdbc:sqlite:CofreDigital.db";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/api/users/add", new AddUserHandler());
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
                    int gid = json.has("GID") ? json.get("GID").getAsInt() : null;

                    insertUser(loginName, nome, senha, gid);

                    String response = "User added successfully";
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    String response = "Error adding user";
                    exchange.sendResponseHeaders(500, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // Method not allowed
            }
        }

        private void insertUser(String loginName, String nome, String senha, Integer gid) throws SQLException {
            String sql = "INSERT INTO Usuarios (login_name, nome, senha, numero_acessos, numero_consultas, GID) VALUES (?, ?, ?, 0, 0, ?)";

            try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, loginName);
                pstmt.setString(2, nome);
                pstmt.setString(3, senha);
                if (gid != null) {
                    pstmt.setInt(4, gid);
                } else {
                    pstmt.setNull(4, java.sql.Types.INTEGER);
                }
                pstmt.executeUpdate();
            }
        }
    }
}
