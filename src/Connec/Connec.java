
package Connec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connec {
    private static final String URL = "jdbc:sqlserver://DESKTOP-E8EUEDV:1433;databaseName=QLTN;encrypt=false;";
    private static final String USER = "sa";
    private static final String PASSWORD = "sa";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối thành công!");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Lỗi kết nối: " + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        getConnection();
    }
}

