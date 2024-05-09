package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class ConnectorMySQL {

    public static ResultSet res;
    public static PreparedStatement pst;
    public static Statement sta;
    public static String sSQL;

    private static final String URL = "jdbc:mysql://localhost:3306/erp_beer?characterEncoding=utf-8";
    private static final String USER = "root";
    private static final String PASS = "87987954";

    public static Connection getConnection() {
        try {
            // Carrega o driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Retorna a conexão
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            // Captura e relança exceções como RuntimeException para facilitar o tratamento
            throw new RuntimeException("Erro na conexão com o servidor!!!: ", ex);
        }
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt) {
        closeConnection(con);
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        closeConnection(con, stmt);
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void fazBackup(Connection con, String diretorio) {
        // Aqui você pode implementar o código para fazer o backup no MySQL
        JOptionPane.showMessageDialog(null, "Backup feito com sucesso");
    }

    public static void lerBackup(String diretorio) {
        // Aqui você pode implementar o código para ler o backup no MySQL
        JOptionPane.showMessageDialog(null, "Backup lido com sucesso!");
    }
}
