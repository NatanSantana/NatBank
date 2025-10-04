package conexao;

import java.sql.*;

public class Conexao {
    private static final String url = "jdbc:mysql://localhost:3306/usuariosbanco";
    private static final String user = "root";
    private static final String password = "root123";

    private static Connection conn;

    public static Connection getConexao(){
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(url, user, password);
                return conn;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            return conn;
        }
    }
}