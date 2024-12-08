package shoo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class CreateDatabaseExample {
    public static void main(String[] args) {
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root"; // Replace with your MySQL username
        String password = ""; // Replace with your MySQL password

        // SQL statement to create a new database
        String sql = "CREATE DATABASE shoo";

        // Establish the connection
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            
            // Execute the SQL statement
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully!");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}