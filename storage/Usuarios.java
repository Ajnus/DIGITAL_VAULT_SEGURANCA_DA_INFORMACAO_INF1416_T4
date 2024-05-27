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

public class Usuarios {
    private static final String DATABASE_URL = "jdbc:sqlite:CofreDigital.db";
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/api/usuarios/add", Usuarios::addUsuario);
        server.createContext("/api/usuarios/get", Usuarios::getUsuarios);
        server.createContext("/api/usuarios/update", Usuarios::updateUsuario);
        server.createContext("/api/usuarios/delete", Usuarios::deleteUsuario);
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port " + PORT);
    }

    public static void addUsuario(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(requestBody, JsonObject.class);

                String loginName = json.get("login_name").getAsString();
                int gid = json.get("GID").getAsInt();
                String nome = json.get("nome").getAsString();
                String senha = json.get("senha").getAsString();
                int numeroAcessos = json.has("numero_acessos") ? json.get("numero_acessos").getAsInt() : 0;
                int numeroConsultas = json.has("numero_consultas") ? json.get("numero_consultas").getAsInt() : 0;

                String sql = "INSERT INTO Usuarios (login_name, GID, nome, senha, numero_acessos, numero_consultas) VALUES (?, ?, ?, ?, ?, ?)";
                try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                        PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, loginName);
                    pstmt.setInt(2, gid);
                    pstmt.setString(3, nome);
                    pstmt.setString(4, senha);
                    pstmt.setInt(5, numeroAcessos);
                    pstmt.setInt(6, numeroConsultas);
                    pstmt.executeUpdate();
                }

                String response = "Usuário adicionado com sucesso";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
                String response = "Erro ao adicionar usuário";
                exchange.sendResponseHeaders(500, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }

    public static void getUsuarios(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                StringBuilder result = new StringBuilder();
                String sql = "SELECT * FROM Usuarios";

                try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        ResultSet rs = pstmt.executeQuery()) {

                    while (rs.next()) {
                        int uid = rs.getInt("UID");
                        String loginName = rs.getString("login_name");
                        int gid = rs.getInt("GID");
                        String nome = rs.getString("nome");
                        String senha = rs.getString("senha");
                        int numeroAcessos = rs.getInt("numero_acessos");
                        int numeroConsultas = rs.getInt("numero_consultas");

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("UID", uid);
                        jsonObject.addProperty("login_name", loginName);
                        jsonObject.addProperty("GID", gid);
                        jsonObject.addProperty("nome", nome);
                        jsonObject.addProperty("senha", senha);
                        jsonObject.addProperty("numero_acessos", numeroAcessos);
                        jsonObject.addProperty("numero_consultas", numeroConsultas);

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
                String response = "Erro ao buscar usuários";
                exchange.sendResponseHeaders(500, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }

    public static void updateUsuario(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(requestBody, JsonObject.class);

                int uid = json.get("UID").getAsInt();
                String loginName = json.get("login_name").getAsString();
                int gid = json.get("GID").getAsInt();
                String nome = json.get("nome").getAsString();
                String senha = json.get("senha").getAsString();
                int numeroAcessos = json.get("numero_acessos").getAsInt();
                int numeroConsultas = json.get("numero_consultas").getAsInt();

                String sql = "UPDATE Usuarios SET login_name = ?, GID = ?, nome = ?, senha = ?, numero_acessos = ?, numero_consultas = ? WHERE UID = ?";
                try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                        PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, loginName);
                    pstmt.setInt(2, gid);
                    pstmt.setString(3, nome);
                    pstmt.setString(4, senha);
                    pstmt.setInt(5, numeroAcessos);
                    pstmt.setInt(6, numeroConsultas);
                    pstmt.setInt(7, uid);
                    pstmt.executeUpdate();
                }

                String response = "Usuário atualizado com sucesso";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
                String response = "Erro ao atualizar usuário";
                exchange.sendResponseHeaders(500, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Method not allowed
        }
    }

    public static void deleteUsuario(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            try {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(requestBody, JsonObject.class);

                int uid = json.get("UID").getAsInt(); // ID do usuário a ser excluído

                String sql = "DELETE FROM Usuarios WHERE UID = ?";
                try (Connection conn = DriverManager.getConnection(DATABASE_URL);
                        PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, uid);
                    pstmt.executeUpdate();
                }

                String response = "Usuário excluído com sucesso";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (SQLException e) {
                e.printStackTrace();
                String response = "Erro ao excluir usuário";
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